package com.block.imageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.block.imageapp.adapter.PostAdapter;
import com.block.imageapp.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> postArrayList = new ArrayList<>();

    final String url = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/photos.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONArray data = response.getJSONArray("data");

                            for(int i = 0; i < data.length(); i++){

                                Post post = new Post(
                                        data.getJSONObject(i).getInt("albumId"),
                                        data.getJSONObject(i).getInt("id"),
                                        data.getJSONObject(i).getString("title"),
                                        data.getJSONObject(i).getString("url"),
                                        data.getJSONObject(i).getString("thumbnailUrl")
                                );
                                postArrayList.add(post);
                            }
                            
                            // 어댑터 만들어서 화면에 표시
                            adapter = new PostAdapter(MainActivity.this, postArrayList);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            // 유저한테 알려준다.
                            return;
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

        queue.add(request);

    }
}