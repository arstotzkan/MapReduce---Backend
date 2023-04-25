package utils;

import java.io.Serializable;

public class GPXStatistics implements Serializable {

    private String user;
    private double totalDistance;
    private double averageSpeed;
    private double totalElevation;
    private int totalExerciseTime;

    public GPXStatistics(String username, double totalDistance, double averageSpeed, double totalElevation, int totalExerciseTime) {
        this.user = username;
        this.totalDistance = totalDistance;
        this.averageSpeed = averageSpeed;
        this.totalElevation = totalElevation;
        this.totalExerciseTime = totalExerciseTime;
    }

    public String getUser() {
        return user;
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

    public void setUser(String user) {
        this.user = user;
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

    @Override
    public String toString(){
        return ("{ User : " + this.user + ", Total Distance : " + this.totalDistance + ", Average Speed : " + this.averageSpeed + ", Total Elevation : " + this.totalElevation + ", Total Exercise Time : " + this.totalExerciseTime + " }");
    }
}
