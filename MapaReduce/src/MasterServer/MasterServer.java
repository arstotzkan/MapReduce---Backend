package MasterServer;
import utils.WorkerInfo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MasterServer extends Thread{

	/* Define the socket that receives requests */
	ServerSocket socketForUsers;
	/* Define the socket that is used to handle the connection */
	Socket userProviderSocket;

	ArrayList<WorkerInfo> workers = new ArrayList<WorkerInfo>();

	public MasterServer() {
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
				Thread userThread = new MasterRequestHandler(userProviderSocket, this.workers , memory);
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