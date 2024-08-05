package nextstep.subway.domain.service;

import nextstep.subway.application.dto.PathResponse;
import nextstep.subway.domain.model.PathType;

public interface PathService {
    default boolean pathExists(Long sourceId, Long targetId) {
        return pathExists(sourceId, targetId, PathType.DISTANCE);
    }

    boolean pathExists(Long sourceId, Long targetId, PathType pathType);

    PathResponse findPath(Long sourceId, Long targetId, PathType pathType);
}
