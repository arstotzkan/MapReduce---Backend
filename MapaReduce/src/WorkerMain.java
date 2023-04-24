
import WorkerClient.WorkerClient;

public class WorkerMain {
    public static void main(String args[]) {

        int numberOfWorkers = Integer.parseInt(args[0]);
        for (int i = 0; i < numberOfWorkers; i++){
            System.out.println();
            new WorkerClient().start(); //init
        }

    }
}
