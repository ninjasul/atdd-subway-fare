package nextstep.subway.application.dto;

public class SectionRequest {
    private Long lineId;
    private Long upStationId;
    private Long downStationId;
    private Integer distance;
    private Integer duration;

    public SectionRequest() {
    }

    public SectionRequest(Long upStationId, Long downStationId, Integer weight) {
        this(null, upStationId, downStationId, weight, weight);
    }

    public SectionRequest(Long upStationId, Long downStationId, Integer distance, Integer duration) {
        this(null, upStationId, downStationId, distance, duration);
    }


    public SectionRequest(Long lineId, Long upStationId, Long downStationId, Integer distance, Integer duration) {
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.duration = duration;
    }

    public Long getLineId() {
        return lineId;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public void setUpStationId(Long upStationId) {
        this.upStationId = upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public void setDownStationId(Long downStationId) {
        this.downStationId = downStationId;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
