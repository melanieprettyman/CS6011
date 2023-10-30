import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientHandlerRunnable implements Runnable {
    private Socket clientSocket_;
    private String fileName_;
    private Room room_ = null;
    private String userName_ = null;
    private HashMap<String, String> headers_ = new HashMap();
    static final String rootDir_ = "resources";

    public ClientHandlerRunnable(Socket var1) {
        this.clientSocket_ = var1;
    }

    public String getUserName() {
        return this.userName_;
    }

    public void run() {
        BufferedReader var1 = null;

        try {
            var1 = new BufferedReader(new InputStreamReader(this.clientSocket_.getInputStream()));
            this.parseHeader(var1);
            if (this.headers_.containsKey("sec-websocket-key")) {
                if (MyWebServer.verbose_) {
                    System.out.println("   Web socket was requested...");
                }

                this.handleWebSocket();
                return;
            }

            this.handleRequestedFile(this.clientSocket_.getOutputStream());
            this.clientSocket_.close();
        } catch (NoSuchAlgorithmException | IOException var3) {
            if (MyWebServer.verbose_) {
                System.out.println("Client Handler Error: " + var3.getMessage());
            }

            var3.printStackTrace();
        }

    }

    private void handleWebSocket() throws NoSuchAlgorithmException, IOException {
        OutputStream var1 = this.clientSocket_.getOutputStream();
        String var2 = (String)this.headers_.get("sec-websocket-key");
        MessageDigest var3 = MessageDigest.getInstance("SHA-1");
        String var4 = Base64.getEncoder().encodeToString(var3.digest((var2 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes()));
        var1.write(("HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: " + var4 + "\r\n\r\n").getBytes());
        var1.flush();

        try {
            while(true) {
                this.readWebSocketMessage();
            }
        } catch (Exception var6) {
            if (MyWebServer.verbose_) {
                System.out.println("Exception happened when handling WS request for client: " + this.userName_);
            }

            if (this.room_ != null) {
                this.room_.removeClient(this);
            }

        }
    }

    private void readWebSocketMessage() throws Exception {
        try {
            DataInputStream var1 = new DataInputStream(this.clientSocket_.getInputStream());
            byte[] var2 = var1.readNBytes(2);
            boolean var3 = (var2[0] & 128) > 0;
            int var4 = var2[0] & 15;
            if (var4 == 8) {
                throw new Exception("Connection Closed");
            }

            boolean var5 = (var2[1] & 128) != 0;
            long var6 = (long)(var2[1] & 127);
            if (var6 == 126L) {
                var6 = (long)var1.readUnsignedShort();
            } else if (var6 == 127L) {
                var6 = var1.readLong();
            }

            byte[] var8 = var1.readNBytes(4);
            byte[] var9 = var1.readNBytes((int)var6);

            for(int var10 = 0; (long)var10 < var6; ++var10) {
                var9[var10] ^= var8[var10 % 4];
            }

            String var17 = new String(var9);
            if (MyWebServer.verbose_) {
                System.out.println("Received message: " + var17);
            }

            String[] var11 = var17.split(" ", 2);
            String var12 = var11[0];
            String var14;
            if (var12.equals("join")) {
                if (this.userName_ != null) {
                    System.out.println("WARNING: client is re-joining...");
                }

                String[] var13 = var11[1].split(" ", 2);
                this.userName_ = var13[0];
                var14 = var13[1];
                Room var15 = Room.getRoom(var14);
                this.room_ = var15;
                var15.addClient(this);
                if (this.userName_.equals("Server")) {
                    this.userName_ = "Imposter";
                    System.out.println("WARNING: Client tried to name themself 'Server'");
                }
            } else if (var12.equals("leave")) {
                this.room_.removeClient(this);
                this.room_ = null;
            } else {
                String var18;
                if (this.room_ == null) {
                    var18 = MyJsonCreator.createMessage(this.userName_, "No Room", "You are not currently in a room...");
                    this.sendWebSocketMessage(var18);
                } else {
                    var18 = var11[1];
                    var14 = MyJsonCreator.createMessage(this.userName_, this.room_.getName(), var18);
                    this.room_.sendMessage(var14, true);
                }
            }
        } catch (IOException var16) {
            var16.printStackTrace();
        }

    }

    public synchronized void sendWebSocketMessage(String var1) throws IOException {
        DataOutputStream var2 = new DataOutputStream(this.clientSocket_.getOutputStream());
        var2.writeByte(129);
        if (var1.length() < 126) {
            var2.write(var1.length());
        } else if (var1.length() < 65535) {
            var2.writeByte(126);
            var2.writeShort(var1.length());
        } else {
            var2.writeByte(127);
            var2.writeLong((long)var1.length());
        }

        var2.write(var1.getBytes());
        var2.flush();
    }

    private void handleRequestedFile(OutputStream var1) throws IOException {
        PrintWriter var2 = new PrintWriter(var1, true);
        File var3 = new File(this.fileName_);
        String var4 = "200 OK";
        String var5 = "unknown";
        String var6 = null;
        FileInputStream var9 = null;
        if (MyWebServer.verbose_) {
            System.out.println("   Handle request for: " + this.fileName_);
        }

        long var7;
        if (!var3.exists()) {
            var4 = "404 Not Found";
            var6 = "<html><head></head><body>404: File Not Found</body></html>";
            var7 = (long)var6.length();
            var5 = "text/html";
        } else {
            boolean var10 = false;
            var7 = var3.length();
            var9 = new FileInputStream(var3);
            int var11 = this.fileName_.lastIndexOf(".");
            if (var11 != -1) {
                String var12 = this.fileName_.substring(var11 + 1);
                if (!var12.equals("html") && !var12.equals("css")) {
                    if (!var12.equals("png") && !var12.equals("jpg")) {
                        if (var12.equals("js")) {
                            var5 = "text/javascript";
                            var10 = true;
                        }
                    } else {
                        var5 = "image/" + var12;
                        var10 = true;
                    }
                } else {
                    var5 = "text/" + var12;
                    var10 = true;
                }
            }

            if (!var10) {
                var4 = "400 Bad Request";
                var6 = "<html><head></head><body>400: Bad Request for file " + this.fileName_ + "</body></html>";
                var7 = (long)var6.length();
                var5 = "text/html";
            }
        }

        if (!var4.equals("200 OK") && MyWebServer.verbose_) {
            System.out.println("Sending error of: " + var4);
        }

        var2.println("HTTP/1.1 " + var4);
        var2.println("Server: Java HTTP Server by Davison");
        Date var10001 = new Date();
        var2.println("Date: " + var10001);
        var2.println("Content-type: " + var5);
        var2.println("Content-length: " + var7);
        var2.println("");
        if (var6 == null) {
            var9.transferTo(var1);
        } else {
            var2.println(var6);
        }

        var2.flush();
    }

    private void parseHeader(BufferedReader var1) throws IOException {
        String var2 = var1.readLine();
        this.fileName_ = var2.split(" ")[1];
        if (this.fileName_.equals("/")) {
            this.fileName_ = "/index.html";
        }

        this.fileName_ = "resources" + this.fileName_;
        boolean var3 = false;

        while(!var3) {
            var2 = var1.readLine();
            if (var2.equals("")) {
                var3 = true;
            } else {
                String[] var4 = var2.split(": ");
                this.headers_.put(var4[0].toLowerCase(), var4[1]);
                if (MyWebServer.verbose_ && var4[0].toLowerCase().equals("user-agent")) {
                    String[] var5 = var2.split(" ");
                    System.out.println("   Connecting client is: " + var5[var5.length - 1]);
                }
            }
        }

        boolean var7 = false;
        if (var7) {
            System.out.println("Header fields:");
            Iterator var8 = this.headers_.entrySet().iterator();

            while(var8.hasNext()) {
                Map.Entry var6 = (Map.Entry)var8.next();
                PrintStream var10000 = System.out;
                String var10001 = (String)var6.getKey();
                var10000.println("\t" + var10001 + ": " + (String)var6.getValue());
            }
        }

    }
}
