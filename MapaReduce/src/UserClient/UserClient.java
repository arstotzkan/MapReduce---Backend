package UserClient;

import java.io.*;
import java.net.*;

import utils.GPXFile;
import utils.GPXStatistics;

public class UserClient extends Thread {
	String filename;
	public UserClient(String filename) {
		this.filename = filename;
	}

	public void run() {
		ObjectOutputStream out= null ;
		ObjectInputStream in = null ;
		Socket requestSocket= null ;


		try {
			String host = "localhost";
			/* Create socket for contacting the server on port 4321*/
			requestSocket = new Socket(host,4321);

			/* Create the streams to send and receive data from server */
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			in = new ObjectInputStream(requestSocket.getInputStream());
			/* Write the two integers */
			GPXFile file = new GPXFile(this.filename);
			out.writeObject(file);
			out.flush();
			/* Print the received result from server */
			GPXStatistics stats = (GPXStatistics) in.readObject();
			System.out.println("Server> " + stats.getTotalDistance());

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