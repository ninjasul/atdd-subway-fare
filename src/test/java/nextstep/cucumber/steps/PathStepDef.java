package nextstep.cucumber.steps;

import static nextstep.subway.application.DefaultLineCommandService.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.acceptance.TestFixture;
import nextstep.subway.application.dto.LineRequest;
import nextstep.subway.application.dto.SectionRequest;
import nextstep.subway.application.dto.StationRequest;
import nextstep.utils.AcceptanceTest;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {AcceptanceTest.class})
public class PathStepDef implements En {

    @Autowired
    private AcceptanceContext context;

    public PathStepDef() {

        Given("지하철역들을 생성 요청하고", (DataTable table) -> {
            List<Map<String, String>> stations = table.asMaps(String.class, String.class);
            for (Map<String, String> station : stations) {
                String stationName = station.get("name");
                ExtractableResponse<Response> response = TestFixture.createStation(stationName);
                Long stationId = response.jsonPath().getLong("id");
                context.store.put(stationName, stationId);
            }
        });

        Given("지하철 노선을 생성 요청하고", (DataTable table) -> {
            List<Map<String, String>> lines = table.asMaps(String.class, String.class);
            for (Map<String, String> line : lines) {
                Long startStationId = context.store.get(line.get("startStation"));
                Long endStationId = context.store.get(line.get("endStation"));

                if (startStationId == null || endStationId == null) {
                    throw new IllegalArgumentException(STATION_NOT_FOUND_MESSAGE);
                }

                LineRequest lineRequest = new LineRequest(
                    line.get("name"),
                    line.get("color"),
                    startStationId,
                    endStationId,
                    Integer.parseInt(line.get("distance"))
                );
                ExtractableResponse<Response> response = TestFixture.createLine(lineRequest);
                Long lineId = response.jsonPath().getLong("id");
                context.store.put(line.get("name"), lineId);
            }
        });

        Given("구간을 추가 요청하고", (DataTable table) -> {
            List<Map<String, String>> sections = table.asMaps(String.class, String.class);
            for (Map<String, String> section : sections) {
                Long lineId = context.store.get(section.get("line"));

                if (lineId == null) {
                    throw new IllegalArgumentException(LINE_NOT_FOUND_MESSAGE);
                }

                Long upStationId = context.store.get(section.get("upStation"));
                Long downStationId = context.store.get(section.get("downStation"));

                if (upStationId == null || downStationId == null) {
                    throw new IllegalArgumentException(STATION_NOT_FOUND_MESSAGE);
                }

                int distance = Integer.parseInt(section.get("distance"));

                TestFixture.addSection(lineId, upStationId, downStationId, distance);
            }
        });

        When("{string}과 {string}의 경로를 조회하면", (String startStation, String endStation) -> {
            Long startStationId = context.store.get(startStation);
            Long endStationId = context.store.get(endStation);

            if (startStationId == null || endStationId == null) {
                throw new IllegalArgumentException(STATION_NOT_FOUND_MESSAGE);
            }

            ExtractableResponse<Response> response = TestFixture.getPaths(startStationId, endStationId);
            context.response = response;
        });


        When("출발역이 null인 경로를 조회하면", () -> {
            ExtractableResponse<Response> response = TestFixture.getPaths(null, context.store.get("강남역"));
            context.response = response;
        });

        When("출발역이 {string}인 경로를 조회하면", (String source) -> {
            Long sourceId = source.equals("null") ? null : Long.parseLong(source);
            ExtractableResponse<Response> response = TestFixture.getPaths(sourceId, context.store.get("강남역"));
            context.response = response;
        });

        When("도착역이 null인 경로를 조회하면", () -> {
            ExtractableResponse<Response> response = TestFixture.getPaths(context.store.get("강남역"), null);
            context.response = response;
        });

        When("도착역이 {string}인 경로를 조회하면", (String target) -> {
            Long targetId = target.equals("null") ? null : Long.parseLong(target);
            ExtractableResponse<Response> response = TestFixture.getPaths(context.store.get("강남역"), targetId);
            context.response = response;
        });

        Then("경로가 정상적으로 조회된다", () -> {
            assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });

        Then("역이 하나만 조회된다", () -> {
            assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(context.response.jsonPath().getList("stations.id", Long.class)).hasSize(1);
        });

        Then("예외가 발생한다", () -> {
            int statusCode = context.response.statusCode();
            assertThat(statusCode).isBetween(400, 599);
        });
    }
}