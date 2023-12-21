package com.block.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText editPercent;
    EditText editNumber;
    Button btnCalculate;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPercent = findViewById(R.id.editPercent);
        editNumber = findViewById(R.id.editNumber);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtResult = findViewById(R.id.txtResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 에디트텍스트에서 유저가 입력한거 가져온다.
                String strPercent = editPercent.getText().toString().trim();
                String strNumber = editNumber.getText().toString().trim();

                // 2. 유저가 입력안했을경우엔
                //   필수항목입력하라고 메세지를 띄운다.
                if(strPercent.isEmpty() || strNumber.isEmpty()){
                    Snackbar.make(btnCalculate,
                            "필수항목을 모두 입력하세요.",
                            Snackbar.LENGTH_LONG).show();
                    return;
                }

                // 3.1 문자를 숫자로 바꾼다.
                double percent = Double.parseDouble(strPercent);
                double number = Double.parseDouble(strNumber);

                // 3.2 수식을 계산한다.
                //    number * percent / 100

                double result = number * percent / 100;

                // 4. 계산결과를 화면에 보여준다.
                txtResult.setText("" + result);

            }
        });
    }
}