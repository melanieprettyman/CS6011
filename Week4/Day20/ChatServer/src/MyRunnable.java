
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class MyRunnable implements Runnable{
    Socket client_;
    String filename = "";

    Boolean masked;

    Room room_;

    Boolean shouldAddClient = true;

    public static HashMap<String, String> messageMap = new HashMap<>();


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
        File file = new File("src" + filename);
        // TODO:
        // FIXME:
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

        // If WS, WS-Communication
        if (httpRequest.requestWebSocket) {
            try {
                while(true) {
                    String message = handleIncommingWebSocketMessages();
                    room_.sendMessageToRoom(message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

   public String handleIncommingWebSocketMessages() throws IOException {
            //Wrap input-stream in data-input-stream to read in groups of bytes
            InputStream is = client_.getInputStream();
            DataInputStream ds = new DataInputStream(is);

                //Read in first two bytes
                byte zeroByte = ds.readByte();
                byte firstByte = ds.readByte();

                //PARSE FOR MASK AND LENGTH
                    //Find maskKey, is it masked?
                    byte maskKey = (byte) (firstByte & 0x80);
                    if (maskKey != 0) {
                        masked = true;
                    }

                    byte opcode = (byte) (zeroByte & 0x0F);
                    //Length is less than 125, store length as B1 only
                    byte length = (byte) (firstByte & 0x7F);

                    //If length is 126, store length as B2-B3
                    if (length == 126) {
                        byte secondToThirdByte = (byte) ds.readShort();
                        length = secondToThirdByte;
                    }
                    //If length is 127, store length as B2-B9
                    else if (length == 127) {
                        byte secondToNinthByte = (byte) ds.readLong();
                        length = secondToNinthByte;
                    }
                //READ IN MASK
                    //If the message is masked reads next 4 bytes (B10-B13) and stores them in the mask array
                    byte[] mask = new byte[4];
                    if (masked) {
                        for (int i = 0; i < mask.length; i++) {
                            mask[i] = ds.readByte();
                        }
                    }
                //READ IN PAYLOAD
                    //Create a byte array for payload with a size equal to length. Read in byte by byte and store in payload.
                    byte[] payload = new byte[length];
                    for (int i = 0; i < payload.length; i++) {
                        payload[i] = ds.readByte();
                    }

                //UN-MASK PAYLOAD
                    //If masked the code enters another loop that ORs each byte of the payload with the corresponding byte of the masking key.
                    if (masked) {
                        for (int i = 0; i < payload.length; i++) {
                            payload[i] = (byte) (payload[i] ^ mask[i % 4]);
                        }
                    }
                //DECODE PAYLOAD
                    //decoding the payload array using the UTF-8 character encoding.
                    String message = new String(payload, StandardCharsets.UTF_8);
        //-----------
        //ROOM-LOGIC
        //-----------

               //CREATE ROOM
               String roomName = extractRoomName(message);
               room_ = Room.getRoom(roomName);
               //LISTEN FOR USERS JOINING AND LEAVING ROOM
               handleClients(message);
               //ADD MSG TO MSG-LOG
                // Check if the message is already in the message history
               if (!room_.messageHistory.contains(message)) {
                   // If the message is not in the message history, add it
                   room_.addMessage(message);
                   return message; // Return the message for further processing
               } else {
                   return null; // Skip sending the already processed message
               }
   }
    public static String extractRoomName(String jsonMessage) {
        String roomName = "";
        String roomname = jsonMessage.split("\"room\":\"") [ 1 ];
        roomName = roomname.split( "\"" ) [ 0 ];
        return roomName;
    }
    public synchronized void handleClients(String message_) throws IOException {
        String message = message_;

        // Step 1: Remove the curly braces from the JSON string
        String jsonContent = message.substring(1, message.length() - 1);

        // Step 2: Split the JSON content into key-value pairs
        String[] keyValuePairs = jsonContent.split(",");

        for (String pair : keyValuePairs) {
            // Split each key-value pair into separate strings for key and value
            String[] keyValue = pair.split(":");
            String key = keyValue[0].replaceAll("\"", "").trim(); // Extract and clean the key
            String value = keyValue[1].replaceAll("\"", "").trim(); // Extract and clean the value

            // Store the extracted key-value pair in a map for reference
            messageMap.put(key, value);

            // Check if the received message indicates a client joining the room
            if (key.equals("type") && value.equals("join")) {
                // If the client is not already in the room, add it and send message history
                if (!room_.clientList.contains(client_)) {
                    room_.addClient(client_);

                    // Send message history to the new client
                    for (String historyMessage : room_.messageHistory) {
                        room_.sendMessageToClient(historyMessage, client_);
                    }

                    // Mark that the client has been added to the room
                    shouldAddClient = false;
                }
            }

            // Check if the received message indicates a client leaving the room
            if (key.equals("type") && value.equals("leave")) {
                room_.removeClient(client_);
            }

            // Send message history to the new client if not previously added to the room
            if (shouldAddClient) {
                for (String historyMessage : room_.messageHistory) {
                    room_.sendMessageToRoom(historyMessage);
                }
            }
        }
    }
}






