package com.block.memoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.block.memoapp.adapter.MemoAdapter;
import com.block.memoapp.api.MemoApi;
import com.block.memoapp.api.NetworkClient;
import com.block.memoapp.config.Config;
import com.block.memoapp.model.Memo;
import com.block.memoapp.model.MemoList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btnAdd;

    // 페이징 처리를 위한 변수들
    int offset = 0;
    int limit = 8;
    int count = 0;

    // 리사이클러뷰 관련 변수들
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인한 유저인지 아닌지를 파악해서
        // 회원가입 액티비티를 띄울것인지, 메인액티비티를 띄울것인지
        // 처리한다.

        // 로그인 했다는것은,
        // 쉐어드프리퍼런스에 JWT 토큰이 있는지 없는지로
        // 파악하면 된다.
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString("token", "");

        if(token.isEmpty()){
            // 토큰이 없다는 것은 로그인한적이 없는것이므로
            // 회원가입 액티비티를 띄우도록 한다.
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
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

                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(lastPosition + 1 == totalCount){
                    // 네트워크 통해서 데이터를 더 불러온다.
                    if( limit == count){
                        // DB에 데이터가 더 존재할수 있으니까, 데이터를 불러온다.
                        addNetworkData();
                    }
                }
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class );
                startActivity(intent);

            }
        });

        getNetworkData();

    }

    private void addNetworkData() {

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
        MemoApi api = retrofit.create(MemoApi.class);
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString("token", "");
        token = "Bearer " + token;

        offset = offset + count;

        Call<MemoList> call = api.getMemoList(token, offset, limit);

        call.enqueue(new Callback<MemoList>() {
            @Override
            public void onResponse(Call<MemoList> call, Response<MemoList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    MemoList memoList = response.body();

                    memoArrayList.addAll( memoList.items );

                    count = memoList.count;

                    adapter.notifyDataSetChanged();

                }else{

                }
            }

            @Override
            public void onFailure(Call<MemoList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getNetworkData(){

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        MemoApi api = retrofit.create(MemoApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString("token", "");
        token = "Bearer " + token;

        Call<MemoList> call = api.getMemoList(token, offset, limit);

        call.enqueue(new Callback<MemoList>() {
            @Override
            public void onResponse(Call<MemoList> call, Response<MemoList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    MemoList memoList = response.body();

                    memoArrayList.addAll( memoList.items );

                    count = memoList.count;

                    // 어댑터 만들어서, 리사이클러뷰에 적용
                    adapter = new MemoAdapter(MainActivity.this, memoArrayList);
                    recyclerView.setAdapter(adapter);


                }else{

                }

            }

            @Override
            public void onFailure(Call<MemoList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}