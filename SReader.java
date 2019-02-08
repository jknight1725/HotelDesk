import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.System.exit;

public class SReader implements Runnable {
    private DataInputStream input;

    public SReader(InputStream is){
        input = new DataInputStream(is);
    }

    public void run(){
        String str = "";
        while (!str.equals("goodbye")){
            try{
                str = input.readUTF();
            }catch(IOException io){
                System.out.println(io);
                exit(5);
            }
            System.out.println(str);
        }
        try {
            input.close();
        }catch(IOException io){
            System.out.println(io);
            exit(6);
        }
        exit(0);
    }
}
