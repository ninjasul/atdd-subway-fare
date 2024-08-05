package nextstep.subway.domain.model;

import java.util.List;

import nextstep.subway.application.DistancePathFinder;
import nextstep.subway.application.DurationPathFinder;
import nextstep.subway.domain.service.PathFinder;

public class Path {
    private final List<Station> stations;
    private final Integer weight;

    public Path(List<Station> stations, Integer weight) {
        this.stations = stations;
        this.weight = weight;
    }

    public static Path ofDistance(List<Line> lines, Station source, Station target) {
        PathFinder pathFinder = new DistancePathFinder(lines);
        Path path = pathFinder.findPath(source, target);
        return new Path(path.getStations(), path.getWeight());
    }

    public static Path ofDuration(List<Line> lines, Station source, Station target) {
        PathFinder pathFinder = new DurationPathFinder(lines);
        Path path = pathFinder.findPath(source, target);
        return new Path(path.getStations(), path.getWeight());
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getWeight() {
        return weight;
    }
}
