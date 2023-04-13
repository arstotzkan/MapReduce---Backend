import UserClient.UserClient;

public class UserMain {
    public static void main(String[] args) {
        new UserClient("gpxs\\route1.gpx").start();
        new UserClient("gpxs\\route2.gpx").start();
        new UserClient("gpxs\\route3.gpx").start();
    }
}
