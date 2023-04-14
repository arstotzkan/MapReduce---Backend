package MasterServer;

import utils.Workload;

import java.io.*;
import java.net.*;
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
				if(request.getClass().getSimpleName().equals("Workload")){
					Workload t = (Workload) request;
					t.setGiven(true);
					out.writeObject(t);
					out.flush();
				}
				else if (request.getClass().getSimpleName().equals("GPXStatistics")){
					//reduce phase propably goes here
					GPXStatistics t = (GPXStatistics) request;
					out.writeObject(t);
					out.flush();
				}

	
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