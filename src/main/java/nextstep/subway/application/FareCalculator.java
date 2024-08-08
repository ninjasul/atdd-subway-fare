package nextstep.subway.application;

public class FareCalculator {
    public static final int BASE_FARE = 1250;
    public static final int EXTRA_FARE_PER_5KM = 100;
    public static final int EXTRA_FARE_PER_8KM = 100;
    public static final int DISTANCE_THRESHOLD_10KM = 10;
    public static final int DISTANCE_THRESHOLD_50KM = 50;
    public static final int UNIT_DISTANCE_5KM = 5;
    public static final int UNIT_DISTANCE_8KM = 8;

    public static int calculateFare(int distance) {
        if (distance <= DISTANCE_THRESHOLD_10KM) {
            return BASE_FARE;
        }

        if (distance <= DISTANCE_THRESHOLD_50KM) {
            int extraFare = (int) Math.ceil((distance - DISTANCE_THRESHOLD_10KM) / (double) UNIT_DISTANCE_5KM) * EXTRA_FARE_PER_5KM;
            return BASE_FARE + extraFare;
        }


        int maxExtraFare10kmTo50km = (int) Math.ceil((DISTANCE_THRESHOLD_50KM - DISTANCE_THRESHOLD_10KM) / (double) UNIT_DISTANCE_5KM) * EXTRA_FARE_PER_5KM;
        int extraFare8km = (int) Math.ceil((distance - DISTANCE_THRESHOLD_50KM) / (double) UNIT_DISTANCE_8KM) * EXTRA_FARE_PER_8KM;
        return BASE_FARE + maxExtraFare10kmTo50km + extraFare8km;
    }
}