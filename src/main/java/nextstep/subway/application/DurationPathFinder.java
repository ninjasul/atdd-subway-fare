package nextstep.subway.application;

import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

import nextstep.subway.domain.model.Line;
import nextstep.subway.domain.model.Section;

public class DurationPathFinder extends AbstractPathFinder {
    public DurationPathFinder(List<Line> lines) {
        super(lines);
    }

    @Override
    protected void addSectionToGraph(Section section) {
        graph.addVertex(section.getUpStation());
        graph.addVertex(section.getDownStation());
        DefaultWeightedEdge edge = graph.addEdge(section.getUpStation(), section.getDownStation());
        graph.setEdgeWeight(edge, section.getDuration());
    }
}
