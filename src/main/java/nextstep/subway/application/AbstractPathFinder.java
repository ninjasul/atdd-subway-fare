package nextstep.subway.application;

import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import nextstep.subway.domain.model.Line;
import nextstep.subway.domain.model.Path;
import nextstep.subway.domain.model.Section;
import nextstep.subway.domain.model.Station;
import nextstep.subway.domain.service.PathFinder;

public abstract class AbstractPathFinder implements PathFinder {
    public static final String PATH_NOT_FOUND_ERROR_MESSAGE = "경로를 찾을 수 없습니다.";

    protected final WeightedMultigraph<Station, DefaultWeightedEdge> graph;
    protected final DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath;

    protected AbstractPathFinder(List<Line> lines) {
        this.graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        initializeGraph(lines);
        this.dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }

    private void initializeGraph(List<Line> lines) {
        lines.forEach(this::addLineToGraph);
    }

    private void addLineToGraph(Line line) {
        line.getOrderedUnmodifiableSections().forEach(this::addSectionToGraph);
    }

    protected abstract void addSectionToGraph(Section section);

    @Override
    public Path findPath(Station source, Station target) {
        GraphPath<Station, DefaultWeightedEdge> path;
        path = dijkstraShortestPath.getPath(source, target);

        if (path == null) {
            throw new IllegalStateException(PATH_NOT_FOUND_ERROR_MESSAGE);
        }

        List<Station> stations = path.getVertexList();
        int weight = (int)path.getWeight();
        return new Path(stations, weight);
    }
}
