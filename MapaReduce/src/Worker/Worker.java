package Worker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker extends Thread {

    /* Define the socket that receives requests */
    ServerSocket server;
    /* Define the socket that is used to handle the connection */
    Socket connection;
    int port;

    public Worker(int port) {
        this.port = port;
    }

    public void run(){
        this.openServer();
    }

    public void openServer() {
        try {

            /* Create Server Socket */
            this.server = new ServerSocket(this.port, 100);
            System.out.println("Worker @ port " + this.port + " ready...");

            while (true) {
                this.connection = this.server.accept();
                WorkerRequestHandler masterThread = new WorkerRequestHandler(this.connection);
                masterThread.start();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                this.server.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
