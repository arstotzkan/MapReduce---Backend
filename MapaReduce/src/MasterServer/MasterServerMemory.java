package MasterServer;

import utils.GPXStatistics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 Returns an ArrayList of statistics read from a .csv file
 @Author Electra FabCap

 **/

public class MasterServerMemory {

    //TODO: merge loadData with constructor since it will literally never be used anywhere else
    private ArrayList<GPXStatistics> statistics; //TODO: probably need to make this static but that requires a minor refactor on code that isnt only mine

    public MasterServerMemory(){
        this.statistics=loadData();
    }

    /**
     * A simple method that iterates through the list of saved GPXStatistics,
     * keeps and aggregates the ones that are made by a certain user
     *
     * @param username the name of the user for whom we're trying to get stats
     * @return a GPXStatistics object with the user's total stats
     */
    public GPXStatistics getTotalStatsForUser(String username){
        double totDist = 0.0;
        double totEle = 0.0;
        int totExTime = 0;

        for (GPXStatistics currStat : this.statistics) {
            if (currStat.getUser().equals(username)){
                totDist += currStat.getTotalDistance();
                totEle += currStat.getTotalElevation();
                totExTime += currStat.getTotalExerciseTimeInSeconds();
            }
        }

        return new GPXStatistics(username, totDist, totEle, totExTime);
    }

    /**
     * A simple method that iterates through the list of saved GPXStatistics,
     * keeps and averages out the ones that are made by a certain user
     *
     * @param username the name of the user for whom we're trying to get stats
     * @return a GPXStatistics object with the user's average stats
     */
    public GPXStatistics getAverageStatsForUser(String username) throws Exception {
        double totDist = 0.0;
        double totEle = 0.0;
        int totExTime = 0;
        int counter = 0;
        for (GPXStatistics currStat : this.statistics) {
            if (currStat.getUser().equals(username)){
                totDist += currStat.getTotalDistance();
                totEle += currStat.getTotalElevation();
                totExTime += currStat.getTotalExerciseTimeInSeconds();
                counter += 1;
            }
        }

        if (counter == 0){
            throw new Exception("There are no records of said user");
        }

        return new GPXStatistics(username, totDist/counter, totEle/counter, totExTime/counter);
    }

    /**
     * A method that returns the total stats of all users
     *
     * @return a GPXStatistics object containing the total stats
     */
    public GPXStatistics getTotalStats(){
        double totDist = 0.0;
        double totEle = 0.0;
        int totExTime = 0;
        for (GPXStatistics currStat : this.statistics) {
            totDist += currStat.getTotalDistance();
            totEle += currStat.getTotalElevation();
            totExTime += currStat.getTotalExerciseTimeInSeconds();
        }

        return new GPXStatistics("TotalStatistics", totDist, totEle, totExTime);
    }

    /**
     * A method that returns the average stats of all users
     *
     * @return a GPXStatistics object containing the average stats
     */
    public GPXStatistics getAverageStats(){
        double totDist = 0.0;
        double totEle = 0.0;
        int totExTime = 0;
        int counter = 0;
        for (GPXStatistics currStat : this.statistics) {
            totDist += currStat.getTotalDistance();
            totEle += currStat.getTotalElevation();
            totExTime += currStat.getTotalExerciseTimeInSeconds();
            counter += 1;
        }

        return new GPXStatistics("AverageStatistics", totDist/counter, totEle/counter, totExTime/counter);
    }


    public ArrayList<GPXStatistics> loadData() {

        ArrayList<GPXStatistics> statistics = new ArrayList<>();
        try{
            File csv_file= new File("src/gpxs/statistics.csv");
            Scanner scanner= new Scanner(csv_file);

                while (scanner.hasNextLine()) {
                    statistics.add(getStatisticsFromLine(scanner.nextLine()));
                }
                scanner.close();
        }

        catch (FileNotFoundException e){
            System.out.println("File not Found");
        }

        return statistics;
    }

    private GPXStatistics getStatisticsFromLine(String l) {

        /*Scanner rowScanner= new Scanner(l);
        System.out.println(l);
        rowScanner.useDelimiter(",");
            String username= rowScanner.next();
            double totalDistance= rowScanner.nextDouble();
            double totalElevation= rowScanner.nextDouble();
            int totalExerciseTime= rowScanner.nextInt();
            GPXStatistics statistics= new GPXStatistics(username, totalDistance, totalElevation, totalExerciseTime);
            rowScanner.close();*/

            String[] stats_str= l.split(",");
            String username= stats_str[0];
            double totalDistance= Double.parseDouble(stats_str[1]);
            double totalElevation= Double.parseDouble(stats_str[2]);
            int totalExerciseTime= Integer.parseInt(stats_str[3]);
            GPXStatistics statistics= new GPXStatistics(username, totalDistance, totalElevation, totalExerciseTime);
            return statistics;

    }

    public ArrayList<GPXStatistics> getStatistics(){
        return this.statistics;
    }

    public void addStatistic(GPXStatistics stat){
        this.statistics.add(stat);
        System.out.println(this.statistics.size());
    }

}
