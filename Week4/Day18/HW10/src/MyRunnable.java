import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
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
        HTTPRequest httpRequest = new HTTPRequest(scanner);
        httpRequest.getRequest(scanner);

//        filename = httpRequest._filename;

        filename = httpRequest.getFileName();
        //Opening the file
        //relative path, bc its using current director, don't need '/', if included, won't find file
        File file = new File("resource" + filename);
        File failfile = new File("/Users/melanieprettyman/Desktop/MSD/CS6011/CS6011/Week1/Day4/MyHttpServer/src/failMessage.html");


        // Get the output stream from the client socket to send the HTTP response
        //we obtain the output stream (outputStream) from the client socket (client). This stream allows
        // us to send data back to the client. and also create a PrintWriter (printWriter) to write text-based
        // data to the output stream.
        OutputStream outputStream = null;

        try {
            outputStream = client_.getOutputStream();
            //Create a httpResponse
            HTTPResponse httpResponse = new HTTPResponse(filename, file, outputStream, failfile, httpRequest);

        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // If WS, start communication
        if (httpRequest.requestWebSocket) {
            try {
                //Wrap input-stream in data-input-stream to read in groups of bytes
                InputStream is = client_.getInputStream();
                DataInputStream ds = new DataInputStream(is);
//                int messageLength = inputStream.read();
//                byte[] messageBytes = new byte[messageLength];
//                inputStream.read(messageBytes);






            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
