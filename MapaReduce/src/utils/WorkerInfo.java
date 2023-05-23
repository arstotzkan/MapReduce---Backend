package utils;

import java.io.Serializable;

public class WorkerInfo implements Serializable {
    final String ip;
    final int port;

    public WorkerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String toString(){
        return this.ip +":"+ this.port;
    }
}
