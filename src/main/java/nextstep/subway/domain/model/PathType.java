package nextstep.subway.domain.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

public enum PathType {
    DISTANCE,
    DURATION
    ;

    private static final Map<String, PathType> PATH_TYPE_MAP = initMap();

    private static Map<String, PathType> initMap() {
        return Arrays.stream(values())
            .collect(Collectors.toMap(PathType::name, Function.identity()));
    }

    public static PathType of(String type) {
        if (!StringUtils.hasText(type)) {
            throw new IllegalArgumentException("PathType이 비어있습니다.");
        }

        if (!PATH_TYPE_MAP.containsKey(type)) {
            throw new IllegalArgumentException("올바르지 않은 PathType 입니다.");
        }

        return PATH_TYPE_MAP.get(type);
    }
}
