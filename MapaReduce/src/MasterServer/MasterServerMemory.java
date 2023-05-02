package MasterServer;

import utils.GPXStatistics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class MasterServerMemory {

    public ArrayList<GPXStatistics> getArrayOfStatistics() {
        ArrayList<GPXStatistics> statistics = new ArrayList<>();
        try{
            File csv_file= new File("src/utils/statistics.csv");
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

        Scanner rowScanner= new Scanner(l);
        rowScanner.useDelimiter(",");
            String username= rowScanner.next();
            double totalDistance=rowScanner.nextDouble();
            double totalElevation=rowScanner.nextDouble();
            int totalExerciseTime=rowScanner.nextInt();
            GPXStatistics statistics= new GPXStatistics(username, totalDistance, totalElevation, totalExerciseTime);
            rowScanner.close();
            return statistics;



    }

}
