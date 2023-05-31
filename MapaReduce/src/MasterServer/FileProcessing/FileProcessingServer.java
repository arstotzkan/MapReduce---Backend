package MasterServer.FileProcessing;
import MasterServer.MasterServerMemory;
import utils.WorkerInfo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileProcessingServer extends Thread{

	/* Define the socket that receives requests */
	ServerSocket socketForUsers;
	/* Define the socket that is used to handle the connection */
	Socket userProviderSocket;

	ArrayList<WorkerInfo> workers;
	MasterServerMemory memory;

	public FileProcessingServer(ArrayList<WorkerInfo> workers, MasterServerMemory memory ) {
		this.workers = workers;
		this.memory = memory;
	}

	public void run(){
		this.openServer();
	}
	public void openServer() {
		try {
			/* Create Server Socket */
			socketForUsers = new ServerSocket(60000, 100); //socket for users
			System.out.println("File processing ready...");

			while (true) {
				/* Accept the connection */
				userProviderSocket = socketForUsers.accept();
				Thread userThread = new FileProcessingRequestHandler(userProviderSocket, this.workers , memory);
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