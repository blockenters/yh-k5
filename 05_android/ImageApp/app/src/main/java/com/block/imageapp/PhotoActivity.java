package com.block.imageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.block.imageapp.model.Post;
import com.bumptech.glide.Glide;

public class PhotoActivity extends AppCompatActivity {

    ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imgPhoto = findViewById(R.id.imgPhoto);

        Post post = (Post) getIntent().getSerializableExtra("post");

        Glide.with(PhotoActivity.this)
                .load(post.url)
                .into(imgPhoto);

    }
}