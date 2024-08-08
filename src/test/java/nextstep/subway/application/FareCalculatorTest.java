package nextstep.subway.application;

import static nextstep.subway.domain.model.FareCalculator.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import nextstep.subway.domain.model.FareCalculator;

class FareCalculatorTest {

    @ParameterizedTest
    @DisplayName("기본 요금 구간일 때 요금은 기본 요금이다")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void 기본_요금_구간일_때_요금은_기본_요금이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE);
    }

    @ParameterizedTest
    @DisplayName("10KM 초과 시 추가 요금이 1번 부과되는 구간일 때 요금은 기본 요금 + 추가 요금 1번이다")
    @ValueSource(ints = {11, 12, 13, 14, 15})
    void _10KM_초과_시_추가_요금이_1번_부과되는_구간일_때_요금은_기본_요금_추가_요금_1번이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE + EXTRA_FARE_PER_5KM);
    }

    @ParameterizedTest
    @DisplayName("10KM 초과 시 추가 요금이 2번 부과되는 구간일 때 요금은 기본 요금 + 추가 요금 2번이다")
    @ValueSource(ints = {16, 17, 18, 19, 20})
    void _10KM_초과_시_추가_요금이_2번_부과되는_구간일_때_요금은_기본_요금_추가_요금_2번이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE + 2 * EXTRA_FARE_PER_5KM);
    }

    @ParameterizedTest
    @DisplayName("10KM 초과 시 추가 요금이 3번 부과되는 구간일 때 요금은 기본 요금 + 추가 요금 3번이다")
    @ValueSource(ints = {21, 22, 23, 24, 25})
    void _10KM_초과_시_추가_요금이_3번_부과되는_구간일_때_요금은_기본_요금_추가_요금_3번이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE + 3 * EXTRA_FARE_PER_5KM);
    }

    @ParameterizedTest
    @DisplayName("10KM 초과 시 추가 요금이 8번 부과되는 구간일 때 요금은 기본 요금 + 추가 요금 8번이다")
    @ValueSource(ints = {46, 47, 48, 49, 50})
    void _10KM_초과_시_추가_요금이_9번_부과되는_구간일_때_요금은_기본_요금_추가_요금_8번이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE + 8 * EXTRA_FARE_PER_5KM);
    }

    @ParameterizedTest
    @DisplayName("50km 초과 구간에 추가 요금이 1번 부과되는 구간일 때 요금은 기본 요금 + 추가 요금 9번이다")
    @ValueSource(ints = {51, 52, 53, 54, 55, 56, 57, 58})
    void _50KM_초과_구간에_추가_요금이_1번_부과되는_구간일_때_요금은_기본_요금_추가_요금_9번_8KM_추가_요금_1번이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE + 9 * EXTRA_FARE_PER_5KM);
    }

    @ParameterizedTest
    @DisplayName("50km 초과 구간에 추가 요금이 2번 부과되는 구간일 때 요금은 기본 요금 + 추가 요금 9번 + 8KM 추가 요금 1번이다")
    @ValueSource(ints = {59, 60, 61, 62, 63, 64, 65, 66})
    void _50KM_초과_구간에_추가_요금이_2번_부과되는_구간일_때_요금은_기본_요금_추가_요금_9번_8KM_추가_요금_1번이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE + 9 * EXTRA_FARE_PER_5KM + EXTRA_FARE_PER_8KM);
    }

    @ParameterizedTest
    @DisplayName("50km 초과 구간에 추가 요금이 3번 부과되는 구간일 때 요금은 기본 요금 + 추가 요금 9번 + 8KM 추가 요금 2번이다")
    @ValueSource(ints = {67, 68, 69, 70, 71, 72, 73, 74})
    void _50KM_초과_구간에_추가_요금이_3번_부과되는_구간일_때_요금은_기본_요금_추가_요금_9번_8KM_추가_요금_2번이다(int distance) {
        // given & when
        int fare = FareCalculator.calculateFare(distance);

        // then
        assertThat(fare).isEqualTo(BASE_FARE + 9 * EXTRA_FARE_PER_5KM + 2 * EXTRA_FARE_PER_8KM);
    }
}
