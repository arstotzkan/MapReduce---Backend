package UserClient;

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
				/* Create socket for contacting the server on port 4321*/
				requestSocket = new Socket(host, 4322);

				/* Create the streams to send and receive data from server */
				out = new ObjectOutputStream(requestSocket.getOutputStream());
				in = new ObjectInputStream(requestSocket.getInputStream());
				/* Write the two integers */
				Workload t = new Workload();
				out.writeObject(t);
				out.flush();
				/* Print the received result from server */
				Workload t2 = (Workload) in.readObject();
				System.out.println("Server>" + t2.isGiven());
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