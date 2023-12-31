import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Files;



public class Main {

    public static void main(String[] args) throws IOException{


        //CREATE SERVER-SOCKET
        // Server-sockets wait for the client (attached to the server)
        ServerSocket server = new ServerSocket(8080);
        String filename = "";

        //SOCKET-FOR-WAITING  for the client using .accept (wait forever same as while true)
        while (true) {
            Socket client = server.accept();

        Thread thread = new Thread(new MyRunnable(client));
        thread.start();
        }
    }
}
