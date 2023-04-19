package MasterServer;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import utils.GPXFile;
import utils.GPXParser;
import utils.GPXStatistics;
import utils.GPXWaypoint;

public class UserRequestHandler extends Thread {
	ObjectInputStream in;
	ObjectOutputStream out;

	public UserRequestHandler(Socket connection) {
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			GPXFile file = (GPXFile) in.readObject();
			GPXStatistics stats = new GPXStatistics(100.0, 10.0, 0.0, 300);
			out.writeObject(stats);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
		//TODO in next couple of days
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
}