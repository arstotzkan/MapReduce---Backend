import UserClient.UserClient;
import utils.GPXFile;
import utils.GPXStatistics;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class TestStats {
    public static void main(String[] args) throws IOException {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Set server IP: ");
        String ip = myObj.nextLine(); // Read user input

        System.out.println("Set username: ");
        String username = myObj.nextLine(); // Read user input

        testUserStats(ip, username);
        testTotalStats(ip);
    }

    private static void testUserStats(String ip, String username){

        DataOutputStream out= null ;
        ObjectInputStream in = null ;
        Socket requestSocket= null ;

        try {
            requestSocket = new Socket(ip,60001);
            out = new DataOutputStream(requestSocket.getOutputStream());

            out.writeUTF(username);
            out.flush();

            in = new ObjectInputStream(requestSocket.getInputStream());
            HashMap<String, GPXStatistics> res = (HashMap<String,GPXStatistics>) in.readObject();
            GPXStatistics userAverages = res.get("userAverageStats");
            GPXStatistics userTotal = res.get("userTotalStats");

            System.out.println("User Average: " + userAverages.toString());
            System.out.println("User Total: " + userTotal.toString());


        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();	out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private static void testTotalStats(String ip){

        ObjectInputStream in = null ;
        ObjectOutputStream out = null;
        Socket requestSocket= null ;


        try {
            requestSocket = new Socket(ip,60002);


            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.writeObject(null);
            out.flush();

            in = new ObjectInputStream(requestSocket.getInputStream());
            HashMap<String, GPXStatistics> res = (HashMap<String,GPXStatistics>) in.readObject();
            GPXStatistics totalAverageStats = res.get("totalAverageStats");
            GPXStatistics totalStats = res.get("totalStats");

            System.out.println("Total Average: " + totalAverageStats.toString());
            System.out.println("Total: " + totalStats.toString());

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
