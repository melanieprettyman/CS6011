package com.example.androidchatclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private EditText editText;
    private ArrayAdapter<String> myAdapter;
    private ArrayAdapter<String> theirAdapter;

    private ArrayList<String> messageList;
    String message;
    ListView messagesListView;
    public static String roomName = "DNG room name";
    public static String userName = "DNG username";
    private static final String WS_URL = "ws://10.0.2.2:8080/endpoint";
    private WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView text = findViewById(R.id.roomNameFeild);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomName = extras.getString(MainActivity.RoomNameKey);
            userName = extras.getString(MainActivity.UserNameKey);

        }
        text.setText("Room: " + roomName);

        // This is where we write the mesage
        editText = findViewById(R.id.editText);

        //Find ListView (in activity_chat). This will we use to display the messages
        messagesListView = findViewById(R.id.messages_view);
        //list of msg's you want to display
        messageList = new ArrayList<>();

        //Create an Adapter that will help the ListView display the messages on the my_message chat bubble, from message list
        myAdapter = new ArrayAdapter<>(this, R.layout.my_message, R.id.message_body, messageList);
        theirAdapter = new ArrayAdapter<>(this, R.layout.their_message, R.id.message_body, messageList);

        //Attach the adapter to the ListView
        messagesListView.setAdapter(myAdapter);
        messagesListView.smoothScrollToPosition(myAdapter.getCount());


        // Initialize WebSocket
       //if this was in the UI thread, it would lock up the thread. saying Asynchronously--the ws library creates a ws thread w.in
        try {
            ws = new WebSocketFactory().createSocket(WS_URL);
            //listen for event and will use our MyWebSocket class to implement them
            ws.addListener(new MyWebsocket(messageList,myAdapter,theirAdapter, messagesListView, this));
            ws.connectAsynchronously();

        } catch (IOException e) {
            //AlertDialog alert = new AlertDialog("Server failed");
            Log.d("chatactivity", "some error");
        }

    }

    //When send button is clicked, call sendMessage
    public void sendMessage(View view) {

        JSONObject JSONMsg = new JSONObject();
        message = editText.getText().toString();
        try {
            JSONMsg.put("type", "message");
            JSONMsg.put("user", userName);
            JSONMsg.put("room", roomName);
            JSONMsg.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editText.getText().clear();

        if (!message.isEmpty()) {
            ws.sendText(String.valueOf(JSONMsg));
        }
        Log.d("chatactivity", "msg was sent");

    }


}







