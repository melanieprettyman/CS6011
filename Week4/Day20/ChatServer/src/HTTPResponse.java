import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

public class HTTPResponse {


    public HashMap<String,String> header_ = new HashMap<>();

    private OutputStream outputStream;



    public HTTPResponse(String filename, File file, OutputStream outputStream, File failFile, HTTPRequest httpRequest) throws IOException, NoSuchAlgorithmException {
    // File exists, send it
    String extension = httpRequest.getExtension(filename);
    header_= httpRequest.header_;

        //Send file
        try{
        sendFile(file, outputStream, extension, httpRequest);
        } catch(FileNotFoundException e){

        // File does not exist, send 404 error
        sendFailFile(failFile, outputStream, extension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        outputStream.flush();
//        outputStream.close();
    }




    public String getContentType(String extension) {
        return switch (extension.toLowerCase()) {
            case "html" -> "text/html";
            case "css" -> "text/css";
            case "jpeg", "jpg" -> // Include additional JPEG extension
                    "image/jpeg";
            default -> "application/octet-stream";
        };
    }
//-------------------------
// GENERATE HTTP RESPONSE
// -------------------------

    private void sendFile(File file, OutputStream outputStream, String extension, HTTPRequest httpRequest) throws IOException, NoSuchAlgorithmException {

        /*If the request includes an "Upgrade" header, it completes the WebSocket handshake by constructing a
        WebSocket-specific response. Otherwise, it handles regular HTTP file requests by sending the requested file content.*/

        //WEB-SERVER RESPONSE
        if (httpRequest.requestWebSocket){
            String encodeKey = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((header_.get("Sec-WebSocket-Key") + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")));

        outputStream.write("HTTP/1.1 101 Switching Protocols\r\n".getBytes());
        outputStream.write("Upgrade: websocket\r\n".getBytes());
        outputStream.write("Connection: Upgrade\r\n".getBytes());
        outputStream.write(("Sec-WebSocket-Accept: " + encodeKey).getBytes());

//            System.out.println("WebSocket Handshake Response:");
//            System.out.println("HTTP/1.1 101 Switching Protocols");
//            System.out.println("Upgrade: websocket");
//            System.out.println("Connection: Upgrade");
//            System.out.println("Sec-WebSocket-Accept: " + encodeKey);
//            System.out.println(); // Empty line to separate headers from body

            outputStream.write("\r\n".getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.flush();
        }
        else {

            //READ REQUESTED FILE
            //Creating a file stream to read the contents of the file

            //Set the Content-type header based on the file extension
            outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        outputStream.write(("Content-type: text/" + extension).getBytes());
                switch (extension) {
                    case "html" -> {
                        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        outputStream.write("Content-type: text/html".getBytes());
                    }
                    case "css" -> {
                        outputStream.write("HTTP/1.1 200 O\r\n".getBytes());
                        outputStream.write("Content-type: text/css".getBytes());
                    }
                    case "jpeg" -> {
                        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        outputStream.write("Content-type: image/jpeg".getBytes());
                    }
                    case "js" -> {
                        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        outputStream.write("Content-type: text/js".getBytes());
                    }

                }

                // Add an empty line to separate headers from the content
                // crucial delimiter that separates the HTTP headers from the content.
                outputStream.write("\r\n".getBytes());



            //SEND-FILE-TO-CLIENT
                // Transfer the file's content directly to the client's output stream
                // use the transferTo method to efficiently transfer the content of the requested file (fileStream) directly
                // to the client's output stream (outputStream).
                // avoids reading the file line by line and is more efficient for sending binary data like images
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.transferTo(outputStream);
            outputStream.flush();
            outputStream.close();
        }
    }

    private void sendFailFile(File file, OutputStream outputStream, String extension) throws IOException {

        //read fail file html
        FileInputStream failFileStream = new FileInputStream(file);

        //UNSuccess header
        outputStream.write("HTTP/1.1 404 NOT OK\n".getBytes());

        //Content header
        outputStream.write("Content-type: text/html\n".getBytes());

        //Header spacer
        outputStream.write("\n".getBytes());

        //Send file
        failFileStream.transferTo(outputStream);

        //Flush and close file
        outputStream.flush();
        outputStream.close();

    }

}

