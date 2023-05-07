import MasterServer.MasterServer;
import Worker.Worker;

import java.util.Scanner;

public class ServerMain {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        int numberOfWorkers;

        if (args.length > 0){
            numberOfWorkers = Integer.parseInt(args[0]);
        } else {
            numberOfWorkers = -1;
            while (numberOfWorkers < 1 || numberOfWorkers > 1000) {
                System.out.println("Set number of workers (1-1000) :");
                numberOfWorkers = scanner.nextInt(); //taking number of workers from args
            }
        }


        System.out.println("Set IP address of workers:");
        String workerIP = scanner.nextLine(); //taking number of workers from args

        new MasterServer(numberOfWorkers, workerIP).start();
    }
}
