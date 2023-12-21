package com.block.cat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editYear;
    Button btnAge;
    TextView txtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editYear = findViewById(R.id.editYear);
        btnAge = findViewById(R.id.btnAge);
        txtAge = findViewById(R.id.txtAge);

        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 유저가 입력한 년도를 가져온다.

                String strYear = editYear.getText().toString().trim();

                if(strYear.isEmpty()){
                    Toast.makeText(MainActivity.this,
                            "년도를 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(strYear);

                // 2, 나이를 계산한다.

                // 현재 년도를 가져온다.
                int now = Calendar.getInstance().get(Calendar.YEAR);

                int age = now - year;

                // 3. 결과를 화면에 보여준다.

                txtAge.setText(age + "살입니다.");

            }
        });

    }
}





