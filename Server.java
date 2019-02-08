//Cody Gardner
import java.io.*;
import java.net.*;

import static java.lang.System.exit;

public class Server {
    static Hotel _mainHotel;

    public static void main(String[] args) {
        ServerSocket mysock = null;
        _mainHotel = new Hotel("Crummy Hotel");

        try {
            mysock = new ServerSocket(7777, 10);
            System.out.println("Socket Created\n");
            while(true){
                Socket notmysock = mysock.accept();
                System.out.println("Connected to " + notmysock.getInetAddress());
                ClientHandler newch = new ClientHandler(notmysock, _mainHotel);
                Thread t = new Thread(newch);
                t.start();
            }
        } catch (IOException io) {
            System.out.println(io);
            exit(1);
        }
    }
}
