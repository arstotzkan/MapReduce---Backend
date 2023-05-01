import MasterServer.MasterServer;
import Worker.Worker;

import java.util.Scanner;

public class ServerMain {
    public static void main(String args[]) {
        int numberOfWorkers = -1;
        // int numberOfWorkers = Integer.parseInt(args[0]); //taking number of workers from args
        while (numberOfWorkers < 1 || numberOfWorkers > 1000) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Set number of workers (1-1000) :");
            numberOfWorkers = myObj.nextInt(); //taking number of workers from args
        }
        new MasterServer(numberOfWorkers).start();
        for (int i = 0; i < numberOfWorkers; i++){
            new Worker(60001 + i).start(); //init
        }

    }
}
