import MasterServer.MasterServer;
import Worker.Worker;

import java.util.Scanner;

public class WorkerMain {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Set IP of master: ");
        String masterIP = scanner.nextLine(); //taking number of workers from args

        System.out.println("Set port of master: ");
        int masterPort = scanner.nextInt(); //taking number of workers from args

        new Worker(masterIP, masterPort ).start(); //init
    }
}
