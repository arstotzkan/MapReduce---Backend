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

	public GPXStatistics reduce(ArrayList<GPXStatistics> chunks){
		double totDist = 0.0;
		double avgSpd = 0.0;
		double totEle = 0.0;
		int totExTime = 0;

		for (GPXStatistics currStat : chunks) {
			totDist += currStat.getTotalDistance();
			totEle += currStat.getTotalElevation();
			totExTime += currStat.getTotalExerciseTime();
			avgSpd += currStat.getAverageSpeed();
		}

		avgSpd = avgSpd / chunks.size(); //maybe this needs some work, cba doing maths while hangover

		return new GPXStatistics(totDist, avgSpd, totEle, totExTime);
	}
}