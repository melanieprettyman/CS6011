package com.example.androidchatclient;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyWebsocket extends WebSocketAdapter {
    // Arrays to store messages and adapters
        // List to store chat messages
        ArrayList<String> messageList_;
        // Adapter for user's messages
        ArrayAdapter<String> myAdapter_;
        // Adapter for other users' messages
        ArrayAdapter<String> theirAdapter_;

    // ListView to display messages
    ListView messagesListView_;

    // ChatActivity object to run code on UI thread
    ChatActivity chatActivity;

    // Constructor for MyWebsocket
    MyWebsocket(ArrayList<String> messageList, ArrayAdapter<String> adapter, ArrayAdapter<String> theirAdapter, ListView messagesListView, ChatActivity chat) {
        // Assigning parameters to class variables
        messageList_ = messageList;
        myAdapter_ = adapter;
        theirAdapter_ = theirAdapter;
        messagesListView_ = messagesListView;
        chatActivity = chat;
    }

    // Method called when WebSocket connection is established
    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        super.onConnected(websocket, headers);

        // Prepare a message to indicate the user has joined the chat room
        JSONObject JSONMsg = new JSONObject();
        try {
            JSONMsg.put("type", "join");
            JSONMsg.put("user", ChatActivity.userName);
            JSONMsg.put("room", ChatActivity.roomName);
            JSONMsg.put("message", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the message through the WebSocket
        websocket.sendText(String.valueOf(JSONMsg));
    }

    // Method called when an error occurs in the WebSocket connection
    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        Log.d("chatactivity", "web socket ERROR");
    }

    // Method called when a text message is received through the WebSocket
    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        super.onTextMessage(websocket, text);

        // Extract message details from the received text
        String displayMsg = "display msg";
        JSONObject jsonObject = new JSONObject(text);
        String type = jsonObject.getString("type");
        String user = jsonObject.getString("user");
        String room = jsonObject.getString("room");
        String message = jsonObject.getString("message");

        // Determine the content of the message based on its type
        if (type.equals("join")) {
            displayMsg = user + " has entered " + room;
        } else if (type.equals("message")) {
            displayMsg = user + ": " + message;
        } else if (type.equals("leave")) {
            displayMsg = user + " left the room";
        }

        // Determine the message style based on the sender
            //if the user-name is equal to the user name of the sender, then the msg belongs to the current user
        boolean belongsToCurrentUser = user.equals(ChatActivity.userName);

        String finalDisplayMsg = displayMsg;

        // Add the message to the ListView as a chat bubble based on its sender
        chatActivity.runOnUiThread(() -> {
            View view = LayoutInflater.from(chatActivity).inflate(belongsToCurrentUser ? R.layout.my_message : R.layout.their_message, null);
            TextView messageBody = view.findViewById(R.id.message_body);
            messageBody.setText(finalDisplayMsg);

            // Set the chat bubble background based on the sender
            if (belongsToCurrentUser) {
                messageBody.setBackgroundResource(R.drawable.my_message);
            } else {
                messageBody.setBackgroundResource(R.drawable.their_message);
            }

            // Add the chat bubble to the ListView
            ((ViewGroup) messagesListView_.getParent()).addView(view);

        });
    }
}


//        messagesListView_.post(new Runnable() {
//            @Override
//            public void run() {
//                // Handle incoming messages and update UI
//                messageList_.add(finalDisplayMsg);
//                // Update the list on the UI
//                if (belongsToCurrentUser) {
//                    myAdapter_.notifyDataSetChanged();
//                    messagesListView_.smoothScrollToPosition(myAdapter_.getCount());
//                } else {
//                    theirAdapter_.notifyDataSetChanged();
//                    messagesListView_.smoothScrollToPosition(theirAdapter_.getCount());
//                }
//            }
//        });