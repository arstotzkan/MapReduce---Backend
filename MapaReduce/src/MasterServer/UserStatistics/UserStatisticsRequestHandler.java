package MasterServer.UserStatistics;

import MasterServer.MasterServerMemory;
import utils.GPXStatistics;
import utils.WorkerInfo;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class UserStatisticsRequestHandler extends Thread{
    DataInputStream in;
    ObjectOutputStream out;
    String sender;
    MasterServerMemory memory;

    public UserStatisticsRequestHandler(Socket connection, MasterServerMemory mem) {
        try {
            this.out = new ObjectOutputStream(connection.getOutputStream());
            this.in = new DataInputStream(connection.getInputStream());
            this.sender =  connection.getRemoteSocketAddress().toString();
            this.memory = mem;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String username = in.readUTF();
            System.out.println("User " + this.sender + " requested data for user: " + username);

            HashMap<String, GPXStatistics> res = new HashMap<String, GPXStatistics>();
            res.put("userAverageStats" , memory.getAverageStatsForUser(username));
            res.put("userTotalStats" , memory.getTotalStatsForUser(username));
            out.writeObject(res);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
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
