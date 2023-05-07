package MasterServer;
import java.io.*;
import java.net.*;

public class MasterServer extends Thread{

	/* Define the socket that receives requests */
	ServerSocket socketForUsers;
	/* Define the socket that is used to handle the connection */
	Socket userProviderSocket;
	final int numberOfWorkers;
	final String workerIP;

	public MasterServer(int numberOfWorkers, String workerIP) {
		this.numberOfWorkers = numberOfWorkers;
		this.workerIP = workerIP;
	}

	public void run(){
		this.openServer();
	}
	public void openServer() {
		try {
			MasterServerMemory memory = new MasterServerMemory();
			/* Create Server Socket */
			socketForUsers = new ServerSocket(60000, 100); //socket for users
			System.out.println("Server ready...");

			while (true) {
				/* Accept the connection */
				userProviderSocket = socketForUsers.accept();
				Thread userThread = new MasterRequestHandler(userProviderSocket, this.numberOfWorkers, this.workerIP , memory);
				userThread.start();
			}

		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				userProviderSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

}