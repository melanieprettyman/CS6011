import java.util.HashMap;
import java.util.Scanner;
public class HTTPRequest {


    public String _filename;
public HashMap<String,String> header_ = new HashMap<>();
Boolean requestWebSocket = false;

//CONSTRUCTOR
public HTTPRequest(Scanner scanner) {
    _filename = getFileName();

}

//HANDLE REQUEST
public void getRequest(Scanner scanner) {
    String _input = "";

    // Log that a client has connected
    System.out.println("Client connected");

    // READ-IN-FILE-NAME
    // Check if there is any data available to read from the client socket.
    // Store the request for the file name
    _input = scanner.nextLine();
    // Extract the requested filename from the HTTP request
    _filename = _input.split(" ")[1];

    // Log the extracted file name
    System.out.println("File Name: " + _filename);

    // If the client is requesting a web server, read in headers line by line and store in a hashmap
    while (!_input.equals("")) {
        // Read the next line of input from the client socket
        System.out.println(_input);
        _input = scanner.nextLine();

        // If the line is not empty, parse it into key and value and store in a hashmap
        if (!_input.equals("")) {
            String key = _input.split(": ")[0];
            String val = _input.split(": ")[1];
            header_.put(key, val);
        }
    }

//    // If the hashmap contains the "Connection" header with a value of "Upgrade," set requestWebSocket to true
//    if (header_.get("Connection").equals("Upgrade")) {
//        requestWebSocket = true;
//    }
    // If the hashmap contains the "Connection" header and its value is "Upgrade", set requestWebSocket to true
    if (header_.containsKey("Connection") && header_.get("Connection").equals("Upgrade")) {
        requestWebSocket = true;
    }

}


//FILE NAME
    // Split the input string using space as a delimiter to separate elements.
    // expecting an HTTP request, and we're interested in the second element,
    // which is the requested filename.
    public String getFileName (){
        return _filename;
    }

//FILE EXTENSION
    public String getExtension (String filename){
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
        return extension;
    }
}