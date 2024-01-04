package com.block.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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
    int limit = 7;

    // 리사이클러뷰 관련 변수들

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


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class );
                startActivity(intent);

            }
        });

        getNetworkData();

    }

    private void getNetworkData(){

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        MemoApi api = retrofit.create(MemoApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String token = sp.getString("token", "");

        Call<MemoList> call = api.getMemoList(token, offset, limit);

        call.enqueue(new Callback<MemoList>() {
            @Override
            public void onResponse(Call<MemoList> call, Response<MemoList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    MemoList memoList = response.body();

                    memoArrayList.addAll( memoList.items );




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