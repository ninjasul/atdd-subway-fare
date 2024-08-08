package nextstep.subway.application.dto;
public class FareCalculator {
    private static final int BASE_FARE = 1250;
    private static final int EXTRA_FARE_PER_5KM = 100;
    private static final int EXTRA_FARE_PER_8KM = 100;
    private static final int DISTANCE_THRESHOLD_10KM = 10;
    private static final int DISTANCE_THRESHOLD_50KM = 50;
    private static final int UNIT_DISTANCE_5KM = 5;
    private static final int UNIT_DISTANCE_8KM = 8;

    public static int calculateFare(int distance) {
        if (distance <= DISTANCE_THRESHOLD_10KM) {
            return BASE_FARE;
        }

        int fare = BASE_FARE;
        fare += calculateOverFare(distance - DISTANCE_THRESHOLD_10KM, DISTANCE_THRESHOLD_50KM - DISTANCE_THRESHOLD_10KM, EXTRA_FARE_PER_5KM, UNIT_DISTANCE_5KM);

        if (distance > DISTANCE_THRESHOLD_50KM) {
            fare += calculateOverFare(distance - DISTANCE_THRESHOLD_50KM, Integer.MAX_VALUE, EXTRA_FARE_PER_8KM, UNIT_DISTANCE_8KM);
        }

        return fare;
    }

    private static int calculateOverFare(int overDistance, int threshold, int farePerUnit, int unitDistance) {
        int unit = overDistance > threshold ? (int) Math.ceil((overDistance - threshold) / (double) unitDistance) : (int) Math.ceil(overDistance / (double) unitDistance);
        return unit * farePerUnit;
    }
}