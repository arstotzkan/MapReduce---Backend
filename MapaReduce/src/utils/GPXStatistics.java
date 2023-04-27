package utils;

import java.io.Serializable;

public class GPXStatistics implements Serializable {

    private String user;
    private double totalDistance;
    private double totalElevation;
    private int totalExerciseTime;

    public GPXStatistics(String username, double totalDistance, double totalElevation, int totalExerciseTime) {
        this.user = username;
        this.totalDistance = totalDistance;
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
        return this.totalDistance/this.totalExerciseTime;
    }

    public double getTotalElevation() {
        return totalElevation;
    }

    public String getTotalExerciseTime() {
        String hours = String.valueOf(totalExerciseTime / 3600);
        String minutes = String.valueOf((totalExerciseTime % 3600) / 60);
        String seconds = String.valueOf(totalExerciseTime % 60);

        if (Integer.parseInt(hours) < 10)
            hours = "0" + hours;
        if (Integer.parseInt(minutes) < 10)
            minutes = "0" + minutes;
        if (Integer.parseInt(seconds) < 10)
            seconds = "0" + seconds;

        return String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds) ;
    }

    public int getTotalExerciseTimeInSeconds() {
        return totalExerciseTime;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setTotalElevation(double totalElevation) {
        this.totalElevation = totalElevation;
    }

    public void setTotalExerciseTime(int totalExerciseTime) {
        this.totalExerciseTime = totalExerciseTime;
    }
    @Override
    public String toString(){
        return ("{ User : " + this.user + ", Total Distance : " + (double) Math.round(this.totalDistance) / 1000 + " (km), Average Speed : " + this.getAverageSpeed()  + " (m/s), Total Elevation : " + (int) this.totalElevation + " (m), Total Exercise Time : " + this.getTotalExerciseTime() + " (hh:mm:ss) }");
    }
}
