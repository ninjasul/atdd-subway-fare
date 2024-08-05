package nextstep.subway.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import nextstep.subway.domain.model.Path;
import nextstep.subway.domain.model.PathType;
import nextstep.subway.domain.model.Station;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PathResponse {
    private List<StationResponse> stations;
    private Integer distance;
    private Integer duration;

    public PathResponse() {
    }

    public PathResponse(List<Station> stations, Integer distance, Integer duration) {
        this.stations = stations.stream()
            .map(station -> new StationResponse(station.getId(), station.getName()))
            .collect(Collectors.toList());
        this.distance = distance;
        this.duration = duration;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public static PathResponse from(Path path, PathType pathType) {
        if (pathType == PathType.DISTANCE) {
            return new PathResponse(path.getStations(), path.getWeight(), null);
        }

        return new PathResponse(path.getStations(), null, path.getWeight());
    }
}

