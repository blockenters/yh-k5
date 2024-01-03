package com.block.youtubeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.block.youtubeapp.adapter.VideoAdapter;
import com.block.youtubeapp.config.Config;
import com.block.youtubeapp.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editKeyword;
    ImageView imgSearch;
    ProgressBar progressBar;


    String keyword;

    RecyclerView recyclerView;
    VideoAdapter adapter;
    ArrayList<Video> videoArrayList = new ArrayList<>();


    // 페이징 처리에 필요한 변수
    String pageToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editKeyword = findViewById(R.id.editKeyword);
        imgSearch = findViewById(R.id.imgSearch);
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 맨 마지막 데이터가, 화면에 나타나면
                // 네트워크 통해서 데이터를 추가로 받아오고, 화면에 표시.
                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                // 스크롤을 맨 끝까지 한 상태 체크
                if( lastPosition + 1 == totalCount ){
                    // 네트워크 통해서 데이터를 추가로 받아오고, 화면에 표시.
                    addNetworkData();
                }

            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                keyword = editKeyword.getText().toString().trim();

                Log.i("AAA", "검색 키워드 : " + keyword);

                if(keyword.isEmpty()){
                    Toast.makeText(MainActivity.this,
                            "키워드를 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                getNetworkData();

            }
        });
    }

    private void addNetworkData() {

        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // 데이터를 페이징처리해서 추가로 가져오는 것이므로,
        // pageToken 파라미터가 있어야 한다.
        String url = Config.SEARCH_URL +
                "?key="+Config.GOOGLE_API_KEY+"&part=snippet&q="+ keyword +"&type=video&maxResults=25&order=date&pageToken="+pageToken;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);

                        try {

                            pageToken = response.getString("nextPageToken");

                            JSONArray items = response.getJSONArray("items");

                            for(int i = 0; i < items.length(); i++){
                                String videoId = items.getJSONObject(i).getJSONObject("id").getString("videoId");
                                String title = items.getJSONObject(i).getJSONObject("snippet").getString("title");
                                String description = items.getJSONObject(i).getJSONObject("snippet").getString("description");
                                String mediumUrl = items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                                String highUrl =  items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url"); ;

                                Video video = new Video(videoId, title, description, mediumUrl, highUrl);

                                videoArrayList.add(video);
                            }

                            // 화면에 보여준다.
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            // 유저한테 알리고.

                            Log.i("AAA", "파싱 에러 : " +e.toString());

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

    // 처음에 데이터를 가져올때 호출.
    private void getNetworkData() {

        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        String url = Config.SEARCH_URL +
                        "?key="+Config.GOOGLE_API_KEY+"&part=snippet&q="+ keyword +"&type=video&maxResults=25&order=date";
        Log.i("AAA", "URL : " +url);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);

                        try {

                            pageToken = response.getString("nextPageToken");

                            JSONArray items = response.getJSONArray("items");

                            for(int i = 0; i < items.length(); i++){
                                String videoId = items.getJSONObject(i).getJSONObject("id").getString("videoId");
                                String title = items.getJSONObject(i).getJSONObject("snippet").getString("title");
                                String description = items.getJSONObject(i).getJSONObject("snippet").getString("description");
                                String mediumUrl = items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                                String highUrl =  items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url"); ;

                                Video video = new Video(videoId, title, description, mediumUrl, highUrl);

                                videoArrayList.add(video);
                            }

                            // 화면에 보여준다.
                            adapter = new VideoAdapter(MainActivity.this, videoArrayList);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            // 유저한테 알리고.

                            Log.i("AAA", "파싱 에러 : " +e.toString());

                            return;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.i("AAA", "발리 에러 : " + error.toString());
                    }
                }

        );

        queue.add(request);
    }
}






