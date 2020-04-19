import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        try {
            // create a socket to a local host with port # 6789
            Socket s = new Socket("localhost", 6789);
            
            System.out.println("A connection is established and I'll now send a message");

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            DataInputStream din = new DataInputStream(s.getInputStream());

            //Thread.sleep(5000);
            dout.writeUTF("Hello Server");
            dout.flush();

            String str = (String) din.readUTF();
            System.out.println("Message received from server: " + str);

            dout.close();
            s.close();

        } catch (IOException e) { System.out.println(e); }
    }
}
