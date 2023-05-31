package MasterServer.WorkerHandler;

import MasterServer.FileProcessing.FileProcessingRequestHandler;
import MasterServer.MasterServerMemory;
import utils.WorkerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WorkerInitServer extends Thread {

    ServerSocket socketForUsers;
    /* Define the socket that is used to handle the connection */
    Socket userProviderSocket;

    ArrayList<WorkerInfo> workers;

    public WorkerInitServer(ArrayList<WorkerInfo> workerList) {
        this.workers = workerList;
    }

    public void run(){
        this.openServer();
    }
    public void openServer() {
        try {
            /* Create Server Socket */
            socketForUsers = new ServerSocket(60003, 100); //socket for users
            System.out.println("Worker init server ready...");

            while (true) {
                /* Accept the connection */
                userProviderSocket = socketForUsers.accept();
                Thread userThread = new WorkerInitRequestHandler(userProviderSocket, this.workers);
                userThread.start();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                userProviderSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
