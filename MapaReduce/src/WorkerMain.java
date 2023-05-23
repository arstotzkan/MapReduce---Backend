import MasterServer.MasterServer;
import Worker.Worker;

import java.util.Scanner;

public class WorkerMain {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Set IP of master: ");
        String masterIP = scanner.nextLine(); //taking number of workers from args

        new Worker(masterIP, 60000 ).start(); //init
    }
}
