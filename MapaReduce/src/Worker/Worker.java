package Worker;

import utils.WorkerInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker extends Thread {

    /* Define the socket that receives requests */
    ServerSocket server;
    /* Define the socket that is used to handle the connection */
    Socket connection;

    final String masterIP;
    final int masterPort;

    public Worker(String masterIP, int masterPort) {
        this.masterIP = masterIP;
        this.masterPort = masterPort;
    }

    public void run(){
        this.openServer();
    }

    public void openServer() {
        try {

            /* Create Server Socket */
            this.server = new ServerSocket(0, 100);
            System.out.println("Worker @ port " + this.server.getLocalPort() + " ready...");

            Socket tempConnection = new Socket(this.masterIP, this.masterPort);
            ObjectOutputStream out = new ObjectOutputStream(tempConnection.getOutputStream());
            String workerIP = tempConnection.getInetAddress().toString().split("/")[1]; //we need raw address (without / in the start)
            out.writeObject(new WorkerInfo(workerIP ,this.server.getLocalPort() ));
            out.flush();
            out.close();

            while (true) {
                this.connection = this.server.accept();
                WorkerRequestHandler masterThread = new WorkerRequestHandler(this.connection);
                masterThread.start();
            }

        } catch (BindException bE){
            System.out.println("The bind error is here: " + this.server.getLocalPort());
        }

        catch (IOException ioException) {
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
