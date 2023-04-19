package MasterServer;
import java.io.*;
import java.net.*;

public class MasterServer {

	/* Define the socket that receives requests */
	ServerSocket socketForUsers;
	ServerSocket socketForWorkers;
	/* Define the socket that is used to handle the connection */
	Socket userProviderSocket;
	Socket workerProviderSocket;
	public void openServer() {
		try {

			/* Create Server Socket */
			socketForUsers = new ServerSocket(4321, 10); //socket for users
			socketForWorkers = new ServerSocket(4322, 10); //socket for workers

			while (true) {
				//TODO: find way to respond to worker when user has sent file

				/* Accept the connection */
				userProviderSocket = socketForUsers.accept();
				System.out.println(userProviderSocket.getRemoteSocketAddress().toString() + "sent:");

				//break into waypoint list
				workerProviderSocket = socketForWorkers.accept();
				System.out.println(workerProviderSocket.getRemoteSocketAddress().toString() + "sent:");
				Thread workerThread = new WorkerRequestHandler(workerProviderSocket);
				workerThread.start();

				Thread userThread = new UserRequestHandler(userProviderSocket);
				userThread.start();
			}

		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				userProviderSocket.close();
				workerProviderSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

}