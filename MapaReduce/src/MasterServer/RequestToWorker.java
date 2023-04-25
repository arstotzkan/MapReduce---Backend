package MasterServer;

import utils.GPXFile;
import utils.GPXStatistics;
import utils.GPXWaypoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RequestToWorker extends Thread{

    Socket connection;
    int port;
    ArrayList<GPXWaypoint> chunk;
    GPXStatistics result = null;

    public RequestToWorker(int port, ArrayList<GPXWaypoint> chunk) {
        this.port = port;
        this.chunk = chunk;
    }

    public void run(){
        ObjectOutputStream out= null ;
        ObjectInputStream in = null ;
        String host = "localhost";
        try {

            /* Create socket for contacting the server on port 4321*/
            this.connection = new Socket(host,this.port);

            /* Create the streams to send and receive data from server */
            out = new ObjectOutputStream(this.connection.getOutputStream());
            in = new ObjectInputStream(this.connection.getInputStream());

            System.out.println("Server sent: " + chunk.toString());
            out.writeObject(chunk);
            out.flush();
            /* Print the received result from server */

            this.result = (GPXStatistics) in.readObject();
            System.out.println("Server got: " + result.toString());

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                this.connection.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public ArrayList<GPXWaypoint> getChunk() {
        return chunk;
    }

    public GPXStatistics getResult() {
        return result;
    }
}
