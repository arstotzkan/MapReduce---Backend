package MasterServer;

import utils.GPXStatistics;
import utils.GPXWaypoint;
import utils.Workload;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class WorkerRequestHandler extends Thread {
	ObjectInputStream in;
	ObjectOutputStream out;
	ArrayList<GPXWaypoint> chunks;

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
					//t.setContent(chunks); //send content
					out.writeObject(t);
					out.flush();
				}
				else if (request.getClass().getSimpleName().equals("GPXStatistics")){
					//reduce phase propably goes here
					GPXStatistics t = (GPXStatistics) request;
					//somehow return this into main program
					Workload res = new Workload();
					out.writeObject(res);
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

	public ArrayList<GPXWaypoint> getChunks() {
		return chunks;
	}

	public void setChunks(ArrayList<GPXWaypoint> chunks) {
		this.chunks = chunks;
	}

	public GPXStatistics reduce(GPXStatistics[] chunks){
		return new GPXStatistics(100.0, 100.0, 0.0, 10);
	}
}