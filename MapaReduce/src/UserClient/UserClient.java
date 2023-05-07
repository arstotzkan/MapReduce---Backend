package UserClient;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import utils.GPXFile;
import utils.GPXStatistics;

public class UserClient extends Thread {
	String filename;
	String serverIP;
	public UserClient(String filename, String ip) {
		this.filename = filename;
		this.serverIP = ip;
	}

	public void run() {
		ObjectOutputStream out= null ;
		ObjectInputStream in = null ;
		Socket requestSocket= null ;


		try {
			/* Create socket for contacting the server on port 60000*/
			requestSocket = new Socket(this.serverIP,60000);

			/* Create the streams to send and receive data from server */
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			in = new ObjectInputStream(requestSocket.getInputStream());
			/* Write the two integers */
			GPXFile file = new GPXFile(this.filename);
			out.writeObject(file);
			out.flush();
			/* Print the received result from server */
			HashMap<String,GPXStatistics> res = (HashMap<String,GPXStatistics>) in.readObject();
			GPXStatistics currentWalkStats = res.get("currentRun");
			GPXStatistics userAverage = res.get("userAverage");
			GPXStatistics totalAverage = res.get("totalAverage");
			System.out.println("Current Walk: " + currentWalkStats.toString());
			System.out.println("User Average: " + userAverage.toString());
			System.out.println("Total Average: " + totalAverage.toString() + "\n");

		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		}catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				in.close();	out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}