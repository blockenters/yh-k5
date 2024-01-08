package com.block.postingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class AddActivity extends AppCompatActivity {

    ImageView imgPhoto;
    EditText editContent;
    Button btnSave;

    // 사진 파일!!
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imgPhoto = findViewById(R.id.imgPhoto);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);


    }
}