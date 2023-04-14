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

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setTotalElevation(double totalElevation) {
        this.totalElevation = totalElevation;
    }

    public void setTotalExerciseTime(int totalExerciseTime) {
        this.totalExerciseTime = totalExerciseTime;
    }
}
