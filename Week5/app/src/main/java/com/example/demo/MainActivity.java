package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String msTag = "MainActivity:MLP";
    static final String RoomNameKey = "RoomNameKey";
    boolean firstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Make a function to do something when the button is clicked
    //Pass the name of the method to the attr. of button for 'on-click'
    public void handleButton(View view) {
        Log.d(msTag, "Button was pressed");
        EditText tv = findViewById(R.id.outputinfold);

        if (firstClick) {
            //"Hello worlds" type, and grab it's id
            tv.setText("Poopy");
            firstClick = false;
        } else {

            //on second click grab info from first act. then start new activtiy
            String roomName = tv.getText().toString();
            Intent intent = new Intent(this, MainChat.class);
            intent.putExtra(RoomNameKey, roomName);
            startActivity(intent);
        }
    }
}