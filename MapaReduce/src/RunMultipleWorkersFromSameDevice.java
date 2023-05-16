import MasterServer.MasterServer;
import Worker.Worker;
import java.util.Scanner;

public class RunMultipleWorkersFromSameDevice {
    /*Made this so you can initialize multiple workers in same pc*/
    public static void main(String args[]) {

        int numberOfWorkers;
        if (args.length > 0){
            numberOfWorkers = Integer.parseInt(args[0]);
        } else {
            numberOfWorkers = -1;
            while (numberOfWorkers < 1 || numberOfWorkers > 1000) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Set number of workers (1-1000) :");
                numberOfWorkers = scanner.nextInt(); //taking number of workers from args
            }
        }

        for (int i = 0; i < numberOfWorkers; i++){
            new Worker().start(); //init
        }
    }
}
