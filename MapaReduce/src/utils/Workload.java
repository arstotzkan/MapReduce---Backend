package utils;

import java.util.ArrayList;

public class Workload {
    private boolean given = false;
    private ArrayList<GPXWaypoint> content= new ArrayList<GPXWaypoint>();
    
    public void setContent(ArrayList<GPXWaypoint> c) {
        content= c;        
    }
    
    public ArrayList<GPXWaypoint> getContent (){
        return content;
    }

    public boolean isGiven() {
        return given;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }
}