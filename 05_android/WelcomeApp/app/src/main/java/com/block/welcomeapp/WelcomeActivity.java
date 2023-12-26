package com.block.welcomeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtWelcome = findViewById(R.id.txtWelcome);

        String email = getIntent().getStringExtra("email");

        txtWelcome.setText(email + "님 환영합니다.");

    }
}