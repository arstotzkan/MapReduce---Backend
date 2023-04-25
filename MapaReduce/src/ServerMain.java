import MasterServer.MasterServer;
import Worker.Worker;

public class ServerMain {
    public static void main(String args[]) {
        new MasterServer().start();

        int numberOfWorkers = Integer.parseInt(args[0]);
        for (int i = 0; i < numberOfWorkers; i++){
            new Worker(6000 + i).start(); //init
        }

    }
}
