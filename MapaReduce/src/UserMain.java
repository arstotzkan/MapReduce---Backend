import UserClient.UserClient;

public class UserMain {
    public static void main(String[] args) {
        new UserClient("src\\gpxs\\route1.gpx").start();
        new UserClient("src\\gpxs\\route2.gpx").start();
        new UserClient("src\\gpxs\\route3.gpx").start();
    }
}
