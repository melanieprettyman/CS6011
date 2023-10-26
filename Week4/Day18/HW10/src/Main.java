import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Main {

    public static void main(String[] args) throws IOException{


        //CREATE SERVER-SOCKET
        // Server-sockets wait for the client (attached to the server)
        ServerSocket server = new ServerSocket(8080);
        String filename = "";

        //SOCKET-FOR-WAITING  for the client using .accept (wait forever same as while true)
        while (true) {
            Socket client = server.accept();
            MyRunnable runnable = new MyRunnable(client);

        Thread thread = new Thread(runnable);
        thread.start();
        }
    }
}
