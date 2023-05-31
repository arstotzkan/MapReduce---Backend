package MasterServer.TotalStatistics;

import MasterServer.MasterServerMemory;
import utils.GPXStatistics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class TotalStatisticsRequestHandler extends Thread{
    ObjectInputStream in;
    ObjectOutputStream out;
    String sender;
    MasterServerMemory memory;

    public TotalStatisticsRequestHandler(Socket connection, MasterServerMemory mem) {
        try {
            this.out = new ObjectOutputStream(connection.getOutputStream());
            this.in = new ObjectInputStream(connection.getInputStream());
            this.sender =  connection.getRemoteSocketAddress().toString();
            this.memory = mem;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println("User " + this.sender + " requested total data");

            HashMap<String, GPXStatistics> res = new HashMap<String, GPXStatistics>();
            res.put("totalAverageStats" , memory.getAverageStats());
            res.put("totalStats" , memory.getTotalStats());
            out.writeObject(res);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
