package MasterServer;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import utils.GPXFile;
import utils.GPXParser;
import utils.GPXStatistics;
import utils.GPXWaypoint;

public class MasterRequestHandler extends Thread {
	ObjectInputStream in;
	ObjectOutputStream out;
	String sender;


	public MasterRequestHandler(Socket connection , ServerSocket workerServerSocket) {
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
			sender =  connection.getRemoteSocketAddress().toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			GPXFile file = (GPXFile) in.readObject();
			System.out.println("User " + this.sender + " sent: " + file.getFilename());
			ArrayList<GPXWaypoint> waypointList = this.breakToWaypoints(file);

			RequestToWorker[] workerThreads = new RequestToWorker[1];

			ArrayList<GPXStatistics> finalStats = new ArrayList<GPXStatistics>();

			for (int i = 0; i < workerThreads.length; i++){
				workerThreads[i] = new RequestToWorker(6000, waypointList);
				workerThreads[i].start();
			}

			for (int i = 0; i < workerThreads.length; i++) {
				workerThreads[i].join();
				finalStats.add(workerThreads[i].getResult());
			}

			//reduce
			GPXStatistics stats = reduce(finalStats);
			System.out.println("User " + this.sender + " got: " + stats.toString());
			out.writeObject(stats);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
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

	public ArrayList<GPXWaypoint> breakToWaypoints(GPXFile file){
		//here we split file into groups of waypoints for workers)

		//get content between <wpt></wpt>
		//get lan/lon
		//get content between <ele></ele>
		//get content between <time></time>
		try{
			String stringData = new String(file.getContent(), StandardCharsets.UTF_8);
			GPXParser parser = new GPXParser();
			return parser.parseGPX(stringData);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<GPXWaypoint>();
		}

	}

	public GPXStatistics reduce(ArrayList<GPXStatistics> chunks){
		String username = chunks.get(0).getUser();
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

		return new GPXStatistics(username, totDist, avgSpd, totEle, totExTime);
	}

}