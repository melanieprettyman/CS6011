package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        Button button = findViewById(R.id.mainChatBtn);

        String info = "nothing";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            info = extras.getString(MainActivity.RoomNameKey);
        }
        button.setText(info);
    }
}