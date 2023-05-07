import UserClient.UserClient;

import java.util.Scanner;

public class UserMain {
    public static void main(String[] args) {
        String dirs = "src\\gpxs\\";
        String name = "route";
        String suf = ".gpx";

        Scanner myObj = new Scanner(System.in);
        System.out.println("Set server IP: ");
        String ip = myObj.nextLine(); // Read user input

        while(true){

            int fileNum = -1;
            while (fileNum < 0 || fileNum > 6){
                  // Create a Scanner object
                System.out.println("Select file from 1-6\n(Press 0 to exit)");
                fileNum = myObj.nextInt(); // Read user input
            }

            if (fileNum == 0) //exit
                break;
            else // read
                new UserClient(dirs + name + fileNum + suf, ip).run();
        }

    }
}
