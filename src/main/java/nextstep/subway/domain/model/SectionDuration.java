package nextstep.subway.domain.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SectionDuration {
    public static final String INVALID_DURATION_MESSAGE = "시간은 0이상 이어야 합니다.";

    @Column(name = "duration", nullable = false)
    private Integer value;

    protected SectionDuration() {
    }

    public SectionDuration(Integer value) {
        if (value < 0) {
            throw new IllegalArgumentException(INVALID_DURATION_MESSAGE);
        }

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SectionDuration that = (SectionDuration)object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
