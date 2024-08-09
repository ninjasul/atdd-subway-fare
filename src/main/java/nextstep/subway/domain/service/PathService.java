package nextstep.subway.domain.service;

import nextstep.subway.application.dto.PathResponse;
import nextstep.subway.domain.model.AgeGroup;
import nextstep.subway.domain.model.PathType;

public interface PathService {
    boolean pathExists(Long sourceId, Long targetId);

    default PathResponse findPath(Long sourceId, Long targetId, PathType pathType) {
        return findPath(sourceId, targetId, pathType, AgeGroup.ADULT);
    }

    PathResponse findPath(Long sourceId, Long targetId, PathType pathType, AgeGroup ageGroup);
}
