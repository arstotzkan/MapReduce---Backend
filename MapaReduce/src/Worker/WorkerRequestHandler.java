package Worker;

import utils.GPXStatistics;
import utils.GPXWaypoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class WorkerRequestHandler extends Thread {
    ObjectInputStream in;
    ObjectOutputStream out;

    public WorkerRequestHandler(Socket connection) {
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
            GPXStatistics stats = calculateStatistics(chunk);
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

    /*Calculates Haversine distance between 2 GPXWaypoints*/
    private static double calculateDist(GPXWaypoint w1, GPXWaypoint w2) {
        double r = 6371 * 1000; //Earth's radius in meters
        double lon1 = Math.toRadians(w1.getLongitude());
        double lon2 = Math.toRadians(w2.getLongitude());
        double lat1 = Math.toRadians(w1.getLatitude());
        double lat2 = Math.toRadians(w2.getLatitude());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));
        return Math.abs((c * r));
    }

    private GPXStatistics calculateStatistics(ArrayList<GPXWaypoint> chunk){
        /*Statistics Calculation*/
        double totalDistance = 0.0;
        int totalExerciseTime= 0;
        double gain = 0.0;
        double loss = 0.0;
        String username = chunk.get(0).getUser();

        GPXWaypoint previousWaypoint = null;
        for (GPXWaypoint currentWaypoint: chunk){
            if (previousWaypoint != null) {
                double distance = calculateDist(previousWaypoint, currentWaypoint);
                totalDistance += distance;
                long timeDiff = currentWaypoint.getDatetime().getTime()- previousWaypoint.getDatetime().getTime();
                double elDiff = currentWaypoint.getElevation() - previousWaypoint.getElevation();
                if (elDiff > 0) {
                    gain += elDiff;
                } else if (elDiff < 0) {
                    loss += Math.abs(elDiff);
                }
                totalExerciseTime += (int) (timeDiff/1000);
            }
            previousWaypoint = currentWaypoint;
        }
        double totalElevation = gain - loss;
        double averageSpeed = totalDistance / totalExerciseTime;

        return new GPXStatistics(username, totalDistance, averageSpeed, totalElevation, totalExerciseTime);
    }

}
