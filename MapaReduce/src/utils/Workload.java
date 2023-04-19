package utils;

public class Workload {
    private boolean given = false;
    private ArrayList<GPXWaypoint> content= new ArrayList<GPXWaypoint>(); 
    
    public void setContent(ArrayList<GPXWapoint> c) {
        content= c;        
    }
    
    public ArrayrList<GPXWaypoint> getContent (){
        return content;
        
    }

    public boolean isGiven() {
        return given;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }
}