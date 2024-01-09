package com.block.postingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.block.postingapp.adapter.PostingAdapter;
import com.block.postingapp.api.NetworkClient;
import com.block.postingapp.api.PostingApi;
import com.block.postingapp.api.UserApi;
import com.block.postingapp.config.Config;
import com.block.postingapp.model.Posting;
import com.block.postingapp.model.PostingList;
import com.block.postingapp.model.Res;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btnAdd;


    // 리사이클러뷰 관련 멤버변수
    RecyclerView recyclerView;
    PostingAdapter adapter;
    ArrayList<Posting> postingArrayList = new ArrayList<>();

    // 페이징 관련 변수
    int offset = 0;
    int limit = 20;
    int count = 0;


    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        token = sp.getString("token", "");

        if(token.isEmpty()){
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class );
            startActivity(intent);
            finish();
            return;
        }


        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(lastPosition + 1 == totalCount){

                    if(count == limit){
                        addNetworkData();
                    }
                }

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        getNetworkData();

    }

    private void addNetworkData() {

        offset = offset + count;

        progressBar.setVisibility(View.VISIBLE);

        // 네트워크로 API 호출한다.
        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        PostingApi api = retrofit.create(PostingApi.class);

        Call<PostingList> call = api.getFriendPosting(token, offset, limit);

        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    PostingList postingList = response.body();

                    count = postingList.count;

                    postingArrayList.addAll( postingList.items );

                    adapter.notifyDataSetChanged();


                }else{

                }
            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    private void getNetworkData() {

        // 변수 초기화
        offset = 0;
        count = 0;

        // 네트워크로 API 호출한다.
        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        PostingApi api = retrofit.create(PostingApi.class);

        token = "Bearer " + token;

        Call<PostingList> call = api.getFriendPosting(token, offset, limit);

        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    PostingList postingList = response.body();

                    count = postingList.count;

                    postingArrayList.clear();

                    postingArrayList.addAll( postingList.items );

                    adapter = new PostingAdapter(MainActivity.this, postingArrayList);

                    recyclerView.setAdapter(adapter);

                }else{

                }

            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuLogout){
            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
            UserApi api = retrofit.create(UserApi.class);

            Call<Res> call = api.logout(token);

            call.enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Call<Res> call, Response<Res> response) {
                    if(response.isSuccessful()){

                        // 쉐어드프리퍼런스의 token 을 없애야 한다.
                        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", "");
                        editor.apply();

                        // 로그인액티비티를 띄우고 메인종료!
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                        finish();

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<Res> call, Throwable t) {

                }
            });

        }

        return super.onOptionsItemSelected(item);
    }
}



