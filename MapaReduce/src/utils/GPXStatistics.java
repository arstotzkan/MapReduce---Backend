package utils;

import java.io.Serializable;

public class GPXStatistics implements Serializable {
    private double totalDistance;
    private double averageSpeed;
    private double totalElevation;
    private int totalExerciseTime;

    public GPXStatistics(double totalDistance, double averageSpeed, double totalElevation, int totalExerciseTime) {
        this.totalDistance = totalDistance;
        this.averageSpeed = averageSpeed;
        this.totalElevation = totalElevation;
        this.totalExerciseTime = totalExerciseTime;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public double getTotalElevation() {
        return totalElevation;
    }

    public int getTotalExerciseTime() {
        return totalExerciseTime;
    }
}
