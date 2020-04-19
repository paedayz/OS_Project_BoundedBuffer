import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        try {
            
            // create a socket at port # 6789
            ServerSocket ss = new ServerSocket(6789);  // create a socket
            System.out.println("A socket is created and now waiting for connection.");

            // establish and wait for an incoming connection
            Socket s = ss.accept();
            System.out.println("A client has made a connection in.");

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // wait for input message and display it
            System.out.println("Waiting for an incoming message...");
            String str = (String) din.readUTF();
            System.out.println("Message received: " + str);

            str = str + " " + str;
            System.out.println("Now I'm going to write this message back:" + str);
            dout.writeUTF(str);

            ss.close();

        } catch (IOException e) { System.out.println(e); }
    }
}
