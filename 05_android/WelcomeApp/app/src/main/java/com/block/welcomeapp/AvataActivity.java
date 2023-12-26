package com.block.welcomeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class AvataActivity extends AppCompatActivity {

    ImageView imgAvata;
    Button btnRabbit;
    Button btnTurtle;
    Button btnOk;

    boolean isSelected = false;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avata);

        imgAvata = findViewById(R.id.imgAvata);
        btnRabbit = findViewById(R.id.btnRabbit);
        btnTurtle = findViewById(R.id.btnTurtle);
        btnOk = findViewById(R.id.btnOk);

        email = getIntent().getStringExtra("email");

        btnRabbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAvata.setImageResource(R.drawable.rabbit);
                isSelected = true;
            }
        });

        btnTurtle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAvata.setImageResource(R.drawable.turtle);
                isSelected = true;
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isSelected == false){
                    Snackbar.make(btnOk,
                            "먼저 아바타를 선택하세요.",
                            Snackbar.LENGTH_SHORT).show();
                    return;
                }
                // 알러트 다이얼로그 띄운다.
                showAlertDialog();
            }
        });


    }


    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AvataActivity.this);
        builder.setCancelable(false);
        builder.setTitle("회원가입 완료");
        builder.setMessage("완료하시겠습니까?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //다음 액티비티를 실행한다.
                Intent intent = new Intent(AvataActivity.this, WelcomeActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 앱 종료

                // 딱 이 액티비티 종료
                finish();
            }
        });
        builder.show();

    }

}



