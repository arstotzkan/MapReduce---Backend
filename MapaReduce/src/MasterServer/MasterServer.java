package MasterServer;

import MasterServer.FileProcessing.FileProcessingServer;
import MasterServer.TotalStatistics.TotalStatisticsServer;
import MasterServer.UserStatistics.UserStatisticsServer;
import MasterServer.WorkerHandler.WorkerInitServer;
import utils.WorkerInfo;

import java.util.ArrayList;

public class MasterServer extends Thread{
    ArrayList<WorkerInfo> workers = new ArrayList<WorkerInfo>();
    MasterServerMemory memory = new MasterServerMemory();
    //servers
    FileProcessingServer fileServer = new FileProcessingServer(workers, memory);
    UserStatisticsServer userStatisticsServer = new UserStatisticsServer(memory);
    TotalStatisticsServer totalStatisticsServer = new TotalStatisticsServer(memory);
    WorkerInitServer workerInitServer = new WorkerInitServer(workers);

    public void run(){
        fileServer.start();
        userStatisticsServer.start();
        totalStatisticsServer.start();
        workerInitServer.start();
    }
}
