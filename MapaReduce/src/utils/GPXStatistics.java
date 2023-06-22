package utils;

import java.io.Serializable;

public class GPXStatistics implements Serializable {

    private final String user;
    private final double totalDistance;
    private final double totalElevation;
    private final int totalExerciseTime;

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
        return (this.totalDistance/this.totalExerciseTime)*3.6;
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

    @Override
    public String toString(){
        return ("{ User : " + this.user + ", Total Distance : " + (double) Math.round(this.totalDistance) / 1000 + " (km), Average Speed : " + (double) Math.round(this.getAverageSpeed() * 100) / 100  + " (m/s), Total Elevation : " + (int) this.totalElevation + " (m), Total Exercise Time : " + this.getTotalExerciseTime() + " (hh:mm:ss) }");
    }

    /**
     * Method used purely for frontend/data visualization purposes, compares a GPXStatistics file to the total
     * @param stats2 a GPXStatistics object obtained from the server [res.get("totalStats")]
     * @return an array with indices: 0 -> distance, 1 -> exercise time, 2 -> elevation, 3 -> average speed
     */
    public double[] compare(GPXStatistics stats2){
        double[] statArray = new double[4];
        double distancePerc = percentageComparison(this.getTotalDistance(), stats2.getTotalDistance());
        double elevationPerc = percentageComparison(this.getTotalElevation(), stats2.getTotalElevation());
        double timePerc = percentageComparison(this.getTotalExerciseTimeInSeconds(), stats2.getTotalExerciseTimeInSeconds());
        double speedPerc = percentageComparison(this.getAverageSpeed(), stats2.getAverageSpeed());
        statArray[0]=distancePerc;
        statArray[1]=timePerc;
        statArray[2]=elevationPerc;
        statArray[3]=speedPerc;
        return statArray;
    }

    private double percentageComparison(double x, double y){
        double z = 100*((x-y)/y);
        return (double)Math.round((z*10)/10);
    }
}
