import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    public static HashMap<String, Room> roomList = new HashMap<>();
    public ArrayList<Socket> clientList = new ArrayList<>();

    private String roomName_;

    public ArrayList<String> messageHistory = new ArrayList<>();


    private Room(String roomName){
        roomName_=roomName;
    }

    public synchronized static Room getRoom( String name ) {
        //looking in hashmap for room name passed in
        Room room = roomList.get( name );
        //if it's not in the hashmap make a new room and add it to the hashmap
        if( room == null ) {
            room = new Room( name );
            roomList.put( name, room );
        }
        return room;

    }
    public synchronized void addClient(Socket client) throws IOException {
        clientList.add(client);
        System.out.println("@addClient Adding client: " + client);

    }
    public synchronized void removeClient(Socket client){
        clientList.remove(client);

    }

    public synchronized void addMessage(String message){
        messageHistory.add(message);
    }
    public synchronized void sendMessageToRoom(String message) throws IOException {
        System.out.println("@sendMessageToRoom Message being sent: " + message);
        for (Socket client : clientList) {
            OutputStream outputStream = client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            // Convert the message to bytes
            byte[] responseBytes = message.getBytes(StandardCharsets.UTF_8);

            // Send opcode
            dataOutputStream.writeByte(0x81); // FIN bit set, opcode for text frame

            // Send payload length
            if (responseBytes.length < 126) {
                dataOutputStream.writeByte(responseBytes.length);
            } else if (responseBytes.length < Math.pow(2, 16)) {
                dataOutputStream.write(126);
                dataOutputStream.writeShort(responseBytes.length);
            } else {
                dataOutputStream.write(127);
                dataOutputStream.writeLong(responseBytes.length);
            }

            // Write the message bytes (Send Payload)
            dataOutputStream.write(responseBytes);

            // Flush the output stream to ensure the message is sent
            dataOutputStream.flush();
        }
    }
    public synchronized void sendMessageToClient(String message, Socket client) throws IOException {
        OutputStream outputStream = client.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        // Convert the message to bytes
        byte[] responseBytes = message.getBytes(StandardCharsets.UTF_8);

        // Send opcode
        dataOutputStream.writeByte(0x81); // FIN bit set, opcode for text frame

        // Send payload length
        if (responseBytes.length < 126) {
            dataOutputStream.writeByte(responseBytes.length);
        } else if (responseBytes.length < Math.pow(2, 16)) {
            dataOutputStream.write(126);
            dataOutputStream.writeShort(responseBytes.length);
        } else {
            dataOutputStream.write(127);
            dataOutputStream.writeLong(responseBytes.length);
        }

        // Write the message bytes (Send Payload)
        dataOutputStream.write(responseBytes);

        // Flush the output stream to ensure the message is sent
        dataOutputStream.flush();
    }


}

//    public synchronized void handleOutgoingWebSocketMessages(String message) throws IOException {
//        OutputStream outputStream = client_.getOutputStream();
//        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//
//        // Convert the message to bytes
//        String responseMessage = message;
//        byte[] responseBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
//
//        //Send opcode
//        dataOutputStream.writeByte(0x81); // FIN bit set, opcode for text frame
//
//        //Send payload length
//        //If msg length is less than 126
//        if (responseBytes.length < 126) {
//            //Put actual length in B1
//            dataOutputStream.writeByte(responseBytes.length);
//        }
//        //If larger than 125, then needs to send the data len over B2-B3 (2 bytes, power 16 bits)
//        else if(responseBytes.length< Math.pow(2,16)){
//            //Put 126 in B1 to let it know to use the next two bytes
//            dataOutputStream.write(126);
//            //Write to next two bytes
//            dataOutputStream.writeShort(responseBytes.length);
//        }
//        else{
//            //Else largest size, send message size as 127 in B1
//            dataOutputStream.write(127);
//            //Write to next B2-B9 btyes
//            dataOutputStream.writeLong(responseBytes.length);
//        }
//
//        // Write the message bytes (Send Payload)
//        dataOutputStream.write(responseBytes);
//
//        // Flush the output stream to ensure the message is sent
//        dataOutputStream.flush();
//
//
//    }