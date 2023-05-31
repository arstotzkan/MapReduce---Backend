package MasterServer.TotalStatistics;

import MasterServer.MasterServerMemory;
import MasterServer.UserStatistics.UserStatisticsRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TotalStatisticsServer extends Thread{
    ServerSocket socketForUsers;
    /* Define the socket that is used to handle the connection */
    Socket userProviderSocket;

    MasterServerMemory memory;

    public TotalStatisticsServer(MasterServerMemory mem) {
        this.memory = mem;
    }

    public void run(){
        this.openServer();
    }
    public void openServer() {
        try {
            /* Create Server Socket */
            socketForUsers = new ServerSocket(60002, 100); //socket for users
            System.out.println("Total statistics server ready...");

            while (true) {
                /* Accept the connection */
                userProviderSocket = socketForUsers.accept();
                Thread userThread = new TotalStatisticsRequestHandler(userProviderSocket, this.memory);
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
