import MasterServer.MasterServer;
import WorkerClient.WorkerClient;

public class ServerMain {
    public static void main(String args[]) {

        new MasterServer().openServer();

        int numberOfWorkers = Integer.parseInt(args[0]);
        for (int i = 0; i < numberOfWorkers; i++){
                new WorkerClient().start(); //init
        }
    }
}
