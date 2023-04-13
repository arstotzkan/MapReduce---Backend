package MasterServer;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import utils.GPXFile;
import utils.GPXStatistics;

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
}