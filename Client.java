import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String args[]){
        String HOSTADDRESS = "";
        String str = "";
        int port = 7777;
        Socket sock = null;
        DataOutputStream output = null;
        Scanner userIn = null;
        //Scanner myScan= new Scanner(System.in);
        try {
            //Get host address from user, will default to Local Host if user gives empty string
            userIn = new Scanner(System.in);
            System.out.print("Enter host address: ");
            HOSTADDRESS = userIn.nextLine();

            //Build socket, prompt user, and build streams
            sock = new Socket(HOSTADDRESS, port);
            System.out.println("Connected to Server...");
            SReader sr = new SReader(sock.getInputStream());
            output = new DataOutputStream(sock.getOutputStream());
            Thread t = new Thread(sr);
            t.start();
        }
        catch(UnknownHostException u){
            //If exception thrown, kill program
            System.out.println(u);
            System.exit(1);
        }
        catch(IOException io){
            //If exception thrown, kill program
            System.out.println(io);
            System.exit(2);
        }

        try {
            while(true) {
                //Take input from user, write to socket
                str = userIn.nextLine();
                output.writeUTF(str);
            }
        }catch (java.io.IOException io){
            //If exception thrown, kill program
            System.out.println(io);
            System.exit(3);
        }
        try {
            sock.close();
        }
        catch(IOException io){
            System.out.println(io);
            System.exit(4);
        }
    }
}
