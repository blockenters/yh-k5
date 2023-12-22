package com.block.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imgAlarm;
    TextView txtTime;
    EditText editTime;
    Button btnCancel;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAlarm = findViewById(R.id.imgAlarm);
        txtTime = findViewById(R.id.txtTime);
        editTime = findViewById(R.id.editTime);
        btnCancel = findViewById(R.id.btnCancel);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTime = editTime.getText().toString().trim();

                if(strTime.isEmpty()){
                    Toast.makeText(MainActivity.this,
                            "타이머 시간을 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 초를 가져온다.
                long time = Integer.parseInt(strTime);
                time = time * 1000;

                // 위의 초에 맞는 타이머를 동작시킨다.
                CountDownTimer countDownTimer = new CountDownTimer(time, 1000 ) {
                    @Override
                    public void onTick(long l) {
                        // 남은 시간을 화면에 표시. 매초마다.
                        long remain = l / 1000;
                        txtTime.setText(  remain+""  );
                    }
                    @Override
                    public void onFinish() {
                        // 타이머가 종료되면 할 작업 작성.
                        // 1. 이미지뷰에 에니메이션 효과를 준다.

                        // 2. 알람소리 나오게 한다.
                    }
                };
                countDownTimer.start();
            }
        });
    }
}





