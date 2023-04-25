package Worker;

import utils.GPXStatistics;
import utils.GPXWaypoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MasterRequestHandler extends Thread {
    ObjectInputStream in;
    ObjectOutputStream out;

    public MasterRequestHandler(Socket connection) {
        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Object request = (Object) in.readObject();
            ArrayList<GPXWaypoint> chunk = (ArrayList<GPXWaypoint>) request;
            //calculate stats

            GPXStatistics stats = new GPXStatistics(1.0, 0.0, 0.0, 0);

            out.writeObject(stats);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(ClassNotFoundException e){
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
