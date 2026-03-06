package com.example.roomdatabase;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Navigate to PersonActivity
        startActivity(new Intent(this, PersonActivity.class));
        finish();
    }
}