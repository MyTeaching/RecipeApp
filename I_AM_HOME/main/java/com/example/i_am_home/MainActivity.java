package com.example.i_am_home; ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.i_am_home.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    protected static final String ACTION_I_AM_HOME = "com.example.I_AM_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "start of onCreate in MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "end of onCreate in MainActivity");
    }

    public void sendTheBroadcast(View view) {
        Log.d(TAG, "start of sendTheBroadcast in MainActivity");
        Intent intent = new Intent();
        intent.setAction(ACTION_I_AM_HOME);

        sendBroadcast(intent);
        Log.d(TAG, "end of sendTheBroadcast in MainActivity");
    }
}