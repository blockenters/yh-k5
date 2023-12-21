package com.block.uitest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    TextView txtHello;
    EditText editName;
    EditText editEmail;
    EditText editPassword;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHello = findViewById(R.id.txtHello);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnSave = findViewById(R.id.btnSave);

        txtHello.setText("Hello~~~~");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 이름 가져오기
                String name = editName.getText().toString().trim();

                // 안드로이드에서 로그 남기는 방법
                Log.i("UITEST MAIN", "유저가 작성한 이름 : " + name);

                // 2. 이메일 주소 가져오기
                String email = editEmail.getText().toString().trim();

                Log.i("UITEST MAIN", "유저가 작성한 이메일 : " + email);

                // 3. 비밀번호 가져오기
                String password = editPassword.getText().toString().trim();

                Log.i("UITEST MAIN", "유저가 작성한 비번 : " + password);

                // 저장 버튼 누르면, 유저가 입력한 이름을,
                // 텍스트뷰에 표시해주세요.

                // txtHello.setText(name);

                // 저장 버튼 누르면, 텍스트뷰가 사라지도록!
                // txtHello.setVisibility(View.INVISIBLE);

                // 유저에게 화면에 보여주는 방법 1
                // Toast 메세지
//                Toast.makeText(MainActivity.this,
//                        "유저가 입력한 이름은 " + name,
//                        Toast.LENGTH_LONG).show();

                // 2. SnackBar  메세지
                Snackbar.make(btnSave,
                        "유저가 입력한 이름은 " + name,
                        Snackbar.LENGTH_LONG).show();

            }
        });

    }
}