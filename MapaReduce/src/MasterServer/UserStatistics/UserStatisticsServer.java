package MasterServer.UserStatistics;

import MasterServer.FileProcessing.FileProcessingRequestHandler;
import MasterServer.MasterServerMemory;
import utils.WorkerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UserStatisticsServer extends Thread{
    /* Define the socket that receives requests */
    ServerSocket socketForUsers;
    /* Define the socket that is used to handle the connection */
    Socket userProviderSocket;

    MasterServerMemory memory;

    public UserStatisticsServer(MasterServerMemory mem) {
        this.memory = mem;
    }

    public void run(){
        this.openServer();
    }
    public void openServer() {
        try {
            /* Create Server Socket */
            socketForUsers = new ServerSocket(60001, 100); //socket for users
            System.out.println("User statistics server ready...");

            while (true) {
                /* Accept the connection */
                userProviderSocket = socketForUsers.accept();
                Thread userThread = new UserStatisticsRequestHandler(userProviderSocket, this.memory);
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
