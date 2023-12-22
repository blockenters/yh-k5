package com.block.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("AAA", "Main : onCreate 함수 실행");

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다른 액티비티를 실행하는 방법

                // 인텐트를 만든다!!
                // 인텐트란?? 어떤 액티비티가 어떤 액티비티를
                // 실행하겠다는 것!
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AAA", "Main : onPause 함수 실행");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AAA", "Main : onStop 함수 실행");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AAA", "Main : onStart 함수 실행");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AAA", "Main : onResume 함수 실행");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AAA", "Main : onDestroy 함수 실행");
    }
}


