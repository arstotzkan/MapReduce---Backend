package utils;

import java.util.Date;

public class GPXWaypoint {
    private double latitude;
    private double longitude;
    private double elevation;
    private Date datetime;

    public GPXWaypoint(double latitude, double longitude, double elevation, Date datetime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.datetime = datetime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public Date getDatetime() {
        return datetime;
    }
}
