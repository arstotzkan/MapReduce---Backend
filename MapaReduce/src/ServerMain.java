import MasterServer.MasterServer;
import Worker.Worker;

public class ServerMain {
    public static void main(String args[]) {
        int numberOfWorkers = Integer.parseInt(args[0]);
        new MasterServer(numberOfWorkers).start();
        for (int i = 0; i < numberOfWorkers; i++){
            new Worker(6001 + i).start(); //init
        }

    }
}
