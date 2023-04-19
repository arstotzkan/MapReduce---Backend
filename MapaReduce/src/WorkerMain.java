import WorkerClient.WorkerClient;

public class WorkerMain {

    public static void main(String[] args) {
        //you need to specify number of workers via cmd
        int numberOfWorkers = Integer.parseInt(args[0]);
        WorkerClient[] workers = new WorkerClient[numberOfWorkers];

        for (int i = 0; i < workers.length; i++){
            workers[i] = new WorkerClient(); //worker init
            workers[i].run();
        }

    }
}
