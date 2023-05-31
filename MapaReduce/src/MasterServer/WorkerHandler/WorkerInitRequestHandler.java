package MasterServer.WorkerHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import MasterServer.MasterServerMemory;
import MasterServer.RequestToWorker;
import utils.*;

public class WorkerInitRequestHandler extends Thread {
    ObjectInputStream in;
    ObjectOutputStream out;
    String sender;
    ArrayList<WorkerInfo> workers;

    public WorkerInitRequestHandler(Socket connection , ArrayList<WorkerInfo> workers) {
        try {
            this.out = new ObjectOutputStream(connection.getOutputStream());
            this.in = new ObjectInputStream(connection.getInputStream());
            this.sender =  connection.getRemoteSocketAddress().toString();
            this.workers = workers;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Object obj = (Object) in.readObject();
            WorkerInfo info = (WorkerInfo) obj; //get worker port
            WorkerInfo newWorker = new WorkerInfo(info.getIP(), info.getPort() );
            workers.add(newWorker);
            System.out.println("Added Worker @ " + newWorker.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}