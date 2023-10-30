import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    public static HashMap<String, Room> roomList = new HashMap<>();
    public ArrayList<MyRunnable> clientList = new ArrayList<>();

    private String roomName_;

    Room(String roomName){
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
}
