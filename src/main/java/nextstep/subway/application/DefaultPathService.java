package nextstep.subway.application;

import static nextstep.subway.application.DefaultLineCommandService.*;

import java.util.List;

import org.springframework.stereotype.Service;

import nextstep.subway.application.dto.PathResponse;
import nextstep.subway.domain.model.Line;
import nextstep.subway.domain.model.Path;
import nextstep.subway.domain.model.PathType;
import nextstep.subway.domain.model.Station;
import nextstep.subway.domain.repository.LineRepository;
import nextstep.subway.domain.repository.StationRepository;
import nextstep.subway.domain.service.PathService;

@Service
public class DefaultPathService implements PathService {
    private final StationRepository stationRepository;
    private final LineRepository lineRepository;

    public DefaultPathService(StationRepository stationRepository, LineRepository lineRepository) {
        this.stationRepository = stationRepository;
        this.lineRepository = lineRepository;
    }

    public PathResponse findPath(Long sourceId, Long targetId, PathType pathType) {
        Station source = findStationOrElseThrow(sourceId);
        Station target = findStationOrElseThrow(targetId);

        List<Line> lines = lineRepository.findAll();
        Path path = Path.of(lines, source, target, pathType);
        return PathResponse.of(path);
    }

    public boolean pathExists(Long sourceId, Long targetId, PathType pathType) {
        Station source = findStationOrElseNull(sourceId);
        Station target = findStationOrElseNull(targetId);

        if (source == null || target == null) {
            return false;
        }

        List<Line> lines = lineRepository.findAll();

        try {
            return (Path.of(lines, source, target, pathType) != null);
        } catch (Exception e) {
            return false;
        }
    }

    private Station findStationOrElseThrow(Long stationId) {
        return stationRepository.findById(stationId)
            .orElseThrow(() -> new IllegalArgumentException(STATION_NOT_FOUND_MESSAGE));
    }

    private Station findStationOrElseNull(Long stationId) {
        return stationRepository.findById(stationId).orElse(null);
    }
}