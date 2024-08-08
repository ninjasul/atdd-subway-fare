package nextstep.subway.domain.model;

import java.util.List;

import nextstep.subway.application.DefaultPathFinder;
import nextstep.subway.domain.service.PathFinder;

public class Path {
    private final List<Station> stations;
    private final int distance;
    private final int duration;

    public Path(List<Station> stations, int distance, int duration) {
        this.stations = stations;
        this.distance = distance;
        this.duration = duration;
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public static Path of(List<Line> lines, Station source, Station target, PathType pathType) {
        PathFinder pathFinder = new DefaultPathFinder(lines);
        return pathFinder.findPath(source, target, pathType);
    }
}
