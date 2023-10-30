import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Room {
    private static HashMap<String, Room> roomMap_ = new HashMap();
    private String name_;
    private ArrayList<ClientHandlerRunnable> clients_ = new ArrayList();
    private ArrayList<String> messageLog_ = new ArrayList();

    public static Room getRoom(String var0) {
        synchronized(roomMap_) {
            Room var2 = (Room)roomMap_.get(var0);
            if (var2 == null) {
                var2 = new Room(var0);
                roomMap_.put(var0, var2);
            }

            return var2;
        }
    }

    public String getName() {
        return this.name_;
    }

    public synchronized void addClient(ClientHandlerRunnable var1) throws IOException {
        Iterator var2 = this.clients_.iterator();

        while(var2.hasNext()) {
            ClientHandlerRunnable var3 = (ClientHandlerRunnable)var2.next();
            String var4 = MyJsonCreator.createJoinMessage(var3.getUserName(), this.name_);
            var1.sendWebSocketMessage(var4);
        }

        var2 = this.messageLog_.iterator();

        while(var2.hasNext()) {
            String var6 = (String)var2.next();
            var1.sendWebSocketMessage(var6);
        }

        if (MyWebServer.verbose_) {
            PrintStream var10000 = System.out;
            String var10001 = var1.getUserName();
            var10000.println(var10001 + " has joined room " + this.name_);
        }

        this.clients_.add(var1);
        String var5 = MyJsonCreator.createJoinMessage(var1.getUserName(), this.name_);
        this.sendMessage(var5, false);
    }

    public synchronized void removeClient(ClientHandlerRunnable var1) throws IOException {
        this.clients_.remove(var1);
        String var2 = MyJsonCreator.createLeaveMessage(var1.getUserName(), this.name_);
        this.sendMessage(var2, false);
    }

    public synchronized void sendMessage(String var1, boolean var2) throws IOException {
        if (MyWebServer.verbose_) {
            PrintStream var10000 = System.out;
            int var10001 = this.clients_.size();
            var10000.println("Sending message to " + var10001 + " clients: " + var1);
        }

        Iterator var3 = this.clients_.iterator();

        while(var3.hasNext()) {
            ClientHandlerRunnable var4 = (ClientHandlerRunnable)var3.next();
            var4.sendWebSocketMessage(var1);
        }

        if (var2) {
            this.messageLog_.add(var1);
        }

    }

    private Room(String var1) {
        if (MyWebServer.verbose_) {
            System.out.println("Creating new room: " + var1);
        }

        this.name_ = var1;
    }
}
