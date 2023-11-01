package com.example.androidchatclient;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyWebsocket extends WebSocketAdapter {
    ArrayList<String> messageList_;
    ArrayAdapter<String> adapter_;
    ListView messagesListView_;

    MyWebsocket(ArrayList<String> messageList, ArrayAdapter<String> adapter, ListView messagesListView) {
        messageList_ = messageList;
        adapter_ = adapter;
        messagesListView_ = messagesListView;
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        super.onConnected(websocket, headers);
        JSONObject JSONMsg = new JSONObject();
        try {
            JSONMsg.put("type", "join");
            JSONMsg.put("user", ChatActivity.userName);
            JSONMsg.put("room", ChatActivity.roomName);
            JSONMsg.put("message", "");

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        websocket.sendText( String.valueOf(JSONMsg));
        Log.d("chatactivity", "web socket is open");
    }


    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        Log.d("chatactivity", "web socket ERROR");
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        super.onTextMessage(websocket, text);
        Log.d("chatactivity", "Incomming message before parse: " +text);

        String displayMsg = "display msg";
        JSONObject jsonObject = new JSONObject(text);

        String type = jsonObject.getString("type");
        String user = jsonObject.getString("user");
        String room = jsonObject.getString("room");
        String message = jsonObject.getString("message");
        Log.d("chatactivity", "type: " + type);


        if (type.equals("join")) {
            displayMsg = user + " has entered " + room;
        }  if (type.equals("message")) {
            displayMsg = user + ": " + message;
        } else if (type.equals("leave")) {
            displayMsg = user + " left the room";
        }
        String finalDisplayMsg = displayMsg;
        Log.d("chatactivity", "Final display message" + finalDisplayMsg);

        messagesListView_.post(new Runnable() {
            @Override
            public void run() {
                // Handle incoming messages and update UI
                messageList_.add(finalDisplayMsg);
                //Update the list on the UI
                adapter_.notifyDataSetChanged();
                messagesListView_.smoothScrollToPosition(adapter_.getCount());
            }
        });


    }
}
