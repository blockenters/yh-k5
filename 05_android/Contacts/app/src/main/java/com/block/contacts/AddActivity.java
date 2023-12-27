package com.block.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(AddActivity.this,
                            "필수항목입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 메인액티비티로 데이터를 돌려준다.
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                setResult(100, intent);

                Toast.makeText(AddActivity.this,
                        "잘 저장되었습니다.",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}



