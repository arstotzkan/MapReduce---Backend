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

	final int WAYPOINTS_PER_CHUNK = 5;
	int numberOfWorkers;

	public MasterRequestHandler(Socket connection , int numberOfWorkers) {
		try {
			this.out = new ObjectOutputStream(connection.getOutputStream());
			this.in = new ObjectInputStream(connection.getInputStream());
			this.sender =  connection.getRemoteSocketAddress().toString();

			this.numberOfWorkers = numberOfWorkers;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			GPXFile file = (GPXFile) in.readObject(); //reading GPXFile object
			System.out.println("User " + this.sender + " sent: " + file.getFilename());

			ArrayList<ArrayList<GPXWaypoint>> listOfChunks = breakFileForWorkers(file); //make a 2d list of breakpoints
			RequestToWorker[] workerThreads = new RequestToWorker[listOfChunks.size()]; //array of threads
			ArrayList<GPXStatistics> finalStats = new ArrayList<GPXStatistics>(); //this is where we store final stats

			for (int i = 0; i < workerThreads.length; i++){
				int workerPort = 6001 + (i % this.numberOfWorkers);
				//implementing round robin here
				//however data might be sent in a different order due to multithreading
				workerThreads[i] = new RequestToWorker(workerPort , listOfChunks.get(i));
				workerThreads[i].start();
			}

			for (int i = 0; i < workerThreads.length; i++) {
				//waiting for all threads to join
				workerThreads[i].join();
				finalStats.add(workerThreads[i].getResult()); //adding to final results
			}

			//reduce
			GPXStatistics stats = reduce(finalStats); //send final stats to user
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

	public ArrayList<ArrayList<GPXWaypoint>> breakFileForWorkers(GPXFile file){
		ArrayList<GPXWaypoint> waypointList = this.breakToWaypoints(file);
		return breakToSublists(waypointList, WAYPOINTS_PER_CHUNK);
	}
	private ArrayList<GPXWaypoint> breakToWaypoints(GPXFile file){
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

	public ArrayList<ArrayList<GPXWaypoint>> breakToSublists(ArrayList<GPXWaypoint> list, int n){

		ArrayList<ArrayList<GPXWaypoint>> finalList = new ArrayList<ArrayList<GPXWaypoint>>();
		ArrayList<GPXWaypoint> temp = new ArrayList<GPXWaypoint>();
		finalList.add(temp);

		int divider = 3;
		int i = 0;

		for (GPXWaypoint wp : list){
			temp.add(wp);

			if (i % n == n - 1){
				temp = new ArrayList<GPXWaypoint>();
				temp.add(wp);
				finalList.add(temp);
				i = 0;
			}
			i++;
		}
		System.out.println("Broke list to " + finalList.size() + " sublists: " + finalList );
		return finalList;
	}

	public GPXStatistics reduce(ArrayList<GPXStatistics> chunks){
		String username = chunks.get(0).getUser();
		double totDist = 0.0;
		double totEle = 0.0;
		int totExTime = 0;

		for (GPXStatistics currStat : chunks) {
			totDist += currStat.getTotalDistance();
			totEle += currStat.getTotalElevation();
			totExTime += currStat.getTotalExerciseTimeInSeconds();
		}

		return new GPXStatistics(username, totDist, totEle, totExTime);
	}

}