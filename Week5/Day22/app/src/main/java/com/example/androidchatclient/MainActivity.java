package com.example.androidchatclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final String msTag = "MainActivity:MLP";
    static final String RoomNameKey = "RoomNameKey";
    static final String UserNameKey = "UserNameKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        //Make a function to do something when the button is clicked
        //Pass the name of the method to the attr. of button for 'on-click'
        public void handleButton(View view) {
            Log.d(msTag, "Button was pressed");
            EditText user_name = findViewById(R.id.name);
            EditText room_name = findViewById(R.id.roomname);


            //on second click grab info from first act. then start new activtiy
                String userName = user_name.getText().toString();
                String roomName = room_name.getText().toString();

            Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(RoomNameKey, roomName);
                intent.putExtra(UserNameKey, userName);
                startActivity(intent);
            }
    }
