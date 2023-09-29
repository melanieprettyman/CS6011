import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Files;




public class Main {


        public static void main (String[]args) throws IOException {

            //CREATE SERVER-SOCKET
            // Server-sockets wait for the client (attached to the server)
            ServerSocket server = new ServerSocket(8080);
            String filename = "";

            //SOCKET-FOR-WAITING  for the client using .accept (wait forever same as while true)
            while (true) {
                Socket client = server.accept();

                //reading from the client, wrap in scanner stream
                Scanner scanner = new Scanner(client.getInputStream());

                //READ-IN-FILE-NAME
                // Check if there is any data available to read from the client socket.
                if (scanner.hasNext()) {
                    // Read the next line of input from the client socket.
                    String input = scanner.nextLine();



                    // Split the input string using space as a delimiter to separate elements.
                    // expecting an HTTP request, and we're interested in the second element,
                    // which is the requested filename.
                    String regexPattern = "^/+";
                    filename = input.split(" ")[1];
                    filename = filename.replaceAll(regexPattern, "");
                }





                //HEADERS *most needed to make things work*

                //Opening the file
                //relative path, bc its using current director, don't need '/', if included, wont find file
                File file = new File(filename);


                //FILE EXTENSION
                // Initialize an empty string to store the file extension
                String extension = "";
                // Find the last occurrence of the dot (.) character in the filename
                int i = filename.lastIndexOf('.');
                // Check if a dot was found in the filename
                if (i > 0) {
                    // Extract the substring after the last dot, which represents the file extension
                    extension = filename.substring(i + 1);
                    // Print the extracted file extension to the console for debugging
                }

                //READ REQUESTED FILE
                //Creating a file stream to read the contents of the file
                FileInputStream fileStream = new FileInputStream(file);

                // Get the output stream from the client socket to send the HTTP response
                //we obtain the output stream (outputStream) from the client socket (client). This stream allows
                // us to send data back to the client. and also create a PrintWriter (printWriter) to write text-based
                // data to the output stream.
                OutputStream outputStream = client.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream);

                // Check if the requested file does not exist or is not a regular file.
                if (!file.exists() || !file.isFile()) {
                    // If the file is not found, create a 404 Not Found response.
                    String response = "HTTP/1.1 404 Not Found\n" +
                            // Set the response content type to HTML.
                            "Content-type: text/html\n" +
                            // Add a blank line to separate headers from the response body.
                            "\n" +
                            // HTML response body with a 404 message
                            "<html><body><h1>404 Not Found</h1></body></html>";
                    // Convert the response string to bytes and write it to the output stream.
                    outputStream.write(response.getBytes());

                    //file exist
                } else {
                    // Set the Content-type header based on the file extension
                    if (extension.equals("html")) {
                        outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                        outputStream.write("Content-type: text/html\n".getBytes());
                    } else if (extension.equals("css")) {
                        outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                        outputStream.write("Content-type: text/css\n".getBytes());
                    } else if (extension.equals("jpeg")) {
                        outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                        outputStream.write("Content-type: image/jpeg\n".getBytes());
                    }

                    // Add an empty line to separate headers from the content
                    // crucial delimiter that separates the HTTP headers from the content.
                    outputStream.write("\n".getBytes());


                    //SEND-FILE-TO-CLIENT
                    // Transfer the file's content directly to the client's output stream
                    //use the transferTo method to efficiently transfer the content of the requested file (fileStream) directly
                    // to the client's output stream (outputStream).
                    // avoids reading the file line by line and is more efficient for sending binary data like images
                    fileStream.transferTo(outputStream);

                    // Flush the output stream to ensure all data is sent
                    outputStream.flush();
                    outputStream.close();
                }
            }


        }
    }


