import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyRunnable implements Runnable{
    Socket client_;
    String filename = "";

    MyRunnable(Socket client){
        client_ = client;
    }
    @Override
    public void run() {
        //Reading input from the client, wrap in scanner stream
        Scanner scanner = null;
        try {
            scanner = new Scanner(client_.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //READ-&-GRAB-FILE-NAME
        //HTTPequest class
        filename = HTTPRequest.getFileName(HTTPRequest.getRequest(scanner));


        //Opening the file
        //relative path, bc its using current director, don't need '/', if included, won't find file
        File file = new File(filename);
        File failfile = new File("/Users/melanieprettyman/Desktop/MSD/CS6011/CS6011/Week1/Day4/MyHttpServer/src/failMessage.html");


        // Get the output stream from the client socket to send the HTTP response
        //we obtain the output stream (outputStream) from the client socket (client). This stream allows
        // us to send data back to the client. and also create a PrintWriter (printWriter) to write text-based
        // data to the output stream.
        OutputStream outputStream = null;

        try {
            outputStream = client_.getOutputStream();
            //Create a httpResponse
            HTTPResponse httpResponse = new HTTPResponse(filename, file, outputStream, failfile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
