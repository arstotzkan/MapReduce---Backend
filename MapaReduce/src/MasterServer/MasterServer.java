package MasterServer;
import java.io.*;
import java.net.*;

public class MasterServer extends Thread{

	/* Define the socket that receives requests */
	ServerSocket socketForUsers;
	/* Define the socket that is used to handle the connection */
	Socket userProviderSocket;
	final int numberOfWorkers;

	public MasterServer(int numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}

	public void run(){
		this.openServer();
	}
	public void openServer() {
		try {

			/* Create Server Socket */
			socketForUsers = new ServerSocket(6000, 100); //socket for users
			System.out.println("Server ready...");

			while (true) {
				/* Accept the connection */
				userProviderSocket = socketForUsers.accept();
				Thread userThread = new MasterRequestHandler(userProviderSocket, numberOfWorkers);
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