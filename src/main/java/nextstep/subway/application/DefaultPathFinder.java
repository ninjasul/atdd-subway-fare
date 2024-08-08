
package nextstep.subway.application;

import java.util.List;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import nextstep.subway.domain.model.FareCalculator;
import nextstep.subway.domain.model.Line;
import nextstep.subway.domain.model.Path;
import nextstep.subway.domain.model.PathType;
import nextstep.subway.domain.model.Section;
import nextstep.subway.domain.model.Station;
import nextstep.subway.domain.service.PathFinder;

public class DefaultPathFinder implements PathFinder {
    public static final String PATH_NOT_FOUND_ERROR_MESSAGE = "경로를 찾을 수 없습니다.";

    private final WeightedMultigraph<Station, DefaultWeightedEdge> distanceGraph;
    private final WeightedMultigraph<Station, DefaultWeightedEdge> durationGraph;
    private final DijkstraShortestPath<Station, DefaultWeightedEdge> distanceDijkstra;
    private final DijkstraShortestPath<Station, DefaultWeightedEdge> durationDijkstra;

    public DefaultPathFinder(List<Line> lines) {
        this.distanceGraph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        this.durationGraph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        initializeGraphs(lines);
        this.distanceDijkstra = new DijkstraShortestPath<>(distanceGraph);
        this.durationDijkstra = new DijkstraShortestPath<>(durationGraph);
    }

    private void initializeGraphs(List<Line> lines) {
        lines.forEach(this::addLineToGraphs);
    }

    private void addLineToGraphs(Line line) {
        line.getOrderedUnmodifiableSections().forEach(section -> {
            addSectionToGraph(section, distanceGraph, section.getDistance());
            addSectionToGraph(section, durationGraph, section.getDuration());
        });
    }

    private void addSectionToGraph(Section section, WeightedMultigraph<Station, DefaultWeightedEdge> graph, int weight) {
        graph.addVertex(section.getUpStation());
        graph.addVertex(section.getDownStation());
        DefaultWeightedEdge edge = graph.addEdge(section.getUpStation(), section.getDownStation());
        graph.setEdgeWeight(edge, weight);
    }

    @Override
    public Path findPath(Station source, Station target, PathType pathType) {
        GraphPath<Station, DefaultWeightedEdge> graphPath;
        graphPath = getStationGraphPath(source, target, pathType);

        if (graphPath == null) {
            throw new IllegalStateException(PATH_NOT_FOUND_ERROR_MESSAGE);
        }

        List<Station> stations = graphPath.getVertexList();
        int distance = calculateTotalDistance(stations);
        int duration = calculateTotalDuration(stations);
        int fare = FareCalculator.calculateFare(distance);
        return new Path(stations, distance, duration, fare);
    }

    private GraphPath<Station, DefaultWeightedEdge> getStationGraphPath(
        Station source,
        Station target,
        PathType pathType
    ) {
        if (pathType == PathType.DISTANCE) {
            return distanceDijkstra.getPath(source, target);
        }

        return durationDijkstra.getPath(source, target);
    }

    private int calculateTotalDistance(List<Station> stations) {
        return calculateTotalWeight(stations, distanceGraph);
    }

    private int calculateTotalDuration(List<Station> stations) {
        return calculateTotalWeight(stations, durationGraph);
    }

    private int calculateTotalWeight(List<Station> stations, WeightedMultigraph<Station, DefaultWeightedEdge> graph) {
        return IntStream.range(0, stations.size() - 1)
            .map(i -> {
                DefaultWeightedEdge edge = graph.getEdge(stations.get(i), stations.get(i + 1));
                return (int) graph.getEdgeWeight(edge);
            })
            .sum();
    }
}
