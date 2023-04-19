package WorkerClient;

import utils.GPXStatistics;
import utils.Workload;

import java.io.*;
import java.net.*;

public class WorkerClient extends Thread {

	public WorkerClient() {
	}

	public void run() {
		ObjectOutputStream out= null ;
		ObjectInputStream in = null ;
		Socket requestSocket= null ;


		try {
			while (true) {
				String host = "localhost";
				/* Create socket for contacting the server on port 4322*/
				requestSocket = new Socket(host, 4322);

				/* Create the streams to send and receive data from server */
				out = new ObjectOutputStream(requestSocket.getOutputStream());
				in = new ObjectInputStream(requestSocket.getInputStream());
				/* Write the two integers */
				Workload workload = new Workload();
				out.writeObject(workload);
				out.flush();
				/* Print the received result from server */
				workload = (Workload) in.readObject();
				System.out.println("Server>" + workload.isGiven());


				requestSocket = new Socket(host, 4322);
				//TODO: calculate from chunk
				//TODO: send calculated data from chunk
				GPXStatistics stat = new GPXStatistics(0.0, 0.0, 0.0, 0);
				out.writeObject(stat);
				out.flush();

				Workload finalResponse = (Workload) in.readObject();

			}
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