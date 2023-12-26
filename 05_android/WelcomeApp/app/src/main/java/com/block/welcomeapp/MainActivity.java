package com.block.welcomeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    EditText editPassword2;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPassword2 = findViewById(R.id.editPassword2);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();

                if( email.contains("@") == false ){
                    Snackbar.make(btnRegister,
                            "이메일을 바르게 입력하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                String password = editPassword.getText().toString().trim();
                String password2 = editPassword2.getText().toString().trim();

                if(  password.length() < 4 || password.length() > 12 ){
                    Snackbar.make(btnRegister,
                            "비번 길이를 확인하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if( password.equals(password2) == false  ) {
                    Snackbar.make(btnRegister,
                            "비번이 서로 일치하지 않습니다.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // 모두 정상이니까,
                // 두번째 액티비티를 실행한다.

                Intent intent = new Intent(MainActivity.this, AvataActivity.class);
                startActivity(intent);

                finish();

            }
        });

    }
}








