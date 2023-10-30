import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a room in the chat server where clients communicate and exchange messages.
 */
public class Room {
    private static HashMap<String, Room> roomList = new HashMap<>();
    private ArrayList<Socket> clientList = new ArrayList<>();

    private String roomName_;

    public ArrayList<String> messageHistory = new ArrayList<>();


    private Room(String roomName){
        roomName_=roomName;
    }

    public ArrayList getClientList(){
        return clientList;
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

    //Add client to client-list array
    public synchronized void addClient(Socket client) throws IOException {
        clientList.add(client);
    }

    //Remove client from client-list array
    public synchronized void removeClient(Socket client){
        clientList.remove(client);

    }

    //Add message to arraylist of message-history
    public synchronized void addMessage(String message){
        messageHistory.add(message);
    }

    //Send message to every client in the room
    public synchronized void sendMessageToRoom(String message) throws IOException {
        System.out.println("@sendMessageToRoom Message being sent: " + message);
        for (Socket client : clientList) {
            OutputStream outputStream = client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            constructMsg(message, dataOutputStream);
        }
    }

    //Send message to only one client
    public synchronized void sendMessageToClient(String message, Socket client) throws IOException {
        OutputStream outputStream = client.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        constructMsg(message, dataOutputStream);

    }

    private synchronized void constructMsg(String message, DataOutputStream dataOutputStream) throws IOException {
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
