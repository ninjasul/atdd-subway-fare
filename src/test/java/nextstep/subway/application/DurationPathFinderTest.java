package nextstep.subway.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.subway.domain.model.Line;
import nextstep.subway.domain.model.Path;
import nextstep.subway.domain.model.Section;
import nextstep.subway.domain.model.Station;

class DurationPathFinderTest {
    private DurationPathFinder durationPathFinder;

    private Station 강남역;
    private Station 역삼역;
    private Station 교대역;
    private Station 남부터미널역;
    private Station 신논현역;
    private Station 양재역;

    @BeforeEach
    public void setUp() {
        Line 이호선 = new Line("2호선", "green");
        Line 삼호선 = new Line("3호선", "orange");
        Line 신분당선 = new Line("신분당선", "red");

        강남역 = new Station("강남역");
        역삼역 = new Station("역삼역");
        교대역 = new Station("교대역");
        남부터미널역 = new Station("남부터미널역");
        신논현역 = new Station("신논현역");
        양재역 = new Station("양재역");

        이호선.addSection(new Section(이호선, 교대역, 강남역, 1, 7));
        이호선.addSection(new Section(이호선, 강남역, 역삼역, 1, 5));
        삼호선.addSection(new Section(삼호선, 교대역, 남부터미널역, 1, 3));
        삼호선.addSection(new Section(삼호선, 남부터미널역, 양재역, 1, 5));
        신분당선.addSection(new Section(신분당선, 신논현역, 강남역, 1, 11));
        신분당선.addSection(new Section(신분당선, 강남역, 양재역, 1, 10));

        List<Line> lines = Arrays.asList(이호선, 삼호선, 신분당선);
        durationPathFinder = new DurationPathFinder(lines);
    }

    @Test
    @DisplayName("같은 노선에 존재하는 두 역을 조회하는 경우 경로가 정상적으로 조회된다")
    void findPathInSameLine() {
        // given & when
        Path path = durationPathFinder.findPath(교대역, 역삼역);

        // then
        assertThat(path.getStations()).containsExactly(교대역, 강남역, 역삼역);
        assertThat(path.getWeight()).isEqualTo(12);
    }

    @Test
    @DisplayName("출발역에서 한번 환승을 해야 도착역에 다다를 수 있는 경우 경로가 정상적으로 조회된다")
    void findPathWithOneTransfer() {
        // given & when
        Path path = durationPathFinder.findPath(교대역, 신논현역);

        // then
        assertThat(path.getStations()).containsExactly(교대역, 강남역, 신논현역);
        assertThat(path.getWeight()).isEqualTo(18);
    }

    @Test
    @DisplayName("출발역에서 두번 환승을 해야 도착역에 다다를 수 있는 경우 경로가 정상적으로 조회된다")
    void findPathWithTwoTransfers() {
        // given
        Station source = 신논현역;
        Station target = 남부터미널역;

        // when
        Path path = durationPathFinder.findPath(source, target);

        // then
        assertThat(path.getStations()).containsExactly(신논현역, 강남역, 교대역, 남부터미널역);
        assertThat(path.getWeight()).isEqualTo(21);
    }

    @Test
    @DisplayName("출발역과 도착역이 같은 경우 역이 하나만 조회된다.")
    void findPathWhenSourceAndTargetAreSame() {
        // given & when
        Path path = durationPathFinder.findPath(강남역, 강남역);

        // then
        assertThat(path.getStations()).containsExactly(강남역);
        assertThat(path.getWeight()).isEqualTo(0);
    }

    @Test
    @DisplayName("출발역과 도착역이 연결이 되어 있지 않은 경우 예외가 발생한다")
    void findPathWhenSourceAndTargetAreNotConnected() {
        // given & when & then
        assertThatThrownBy(() -> durationPathFinder.findPath(강남역, new Station("잠실역")))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("조회하려는 출발역이 null인 경우 예외가 발생한다")
    void findPathWhenSourceStationIsNull() {
        // given & when & then
        assertThatThrownBy(() -> durationPathFinder.findPath(null, 강남역))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않은 출발역을 조회하는 경우 예외가 발생한다")
    void findPathWhenSourceStationNotFound() {
        // given & when & then
        assertThatThrownBy(() -> durationPathFinder.findPath(new Station("없는역"), 강남역))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("조회하려는 도착역이 null인 경우 예외가 발생한다")
    void findPathWhenTargetStationIsNull() {
        // given & when & then
        assertThatThrownBy(() -> durationPathFinder.findPath(강남역, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 도착역을 조회하는 경우 예외가 발생한다")
    void findPathWhenTargetStationNotFound() {
        // given & when & then
        assertThatThrownBy(() -> durationPathFinder.findPath(강남역, new Station("없는역")))
            .isInstanceOf(IllegalArgumentException.class);
    }
}