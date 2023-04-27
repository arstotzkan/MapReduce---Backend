package utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class GPXWaypoint implements Serializable {

    private final String user;
    private final double latitude;
    private final double longitude;
    private final double elevation;
    private final Date datetime;

    public GPXWaypoint(String username, double latitude, double longitude, double elevation, Date datetime) {
        this.user =  username;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.datetime = datetime;
    }

    public GPXWaypoint(String username, String latitude, String longitude, String elevation, String datetime) throws ParseException {
        this.user =  username;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.elevation = Double.parseDouble(elevation);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.datetime = formatter.parse(datetime);
    }

    public String getUser() {
        return user;
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

    @Override
    public String toString() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        return ( "{ User: " + this.user + ", Latitude: " + this.latitude + ", Longitude: " + this.longitude + ", Elevation: " + this.elevation + ", DateTime: " + df.format(this.datetime) + " }");
    }
}
