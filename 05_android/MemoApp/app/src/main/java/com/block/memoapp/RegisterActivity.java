package com.block.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.block.memoapp.api.NetworkClient;
import com.block.memoapp.api.UserApi;
import com.block.memoapp.config.Config;
import com.block.memoapp.model.User;
import com.block.memoapp.model.UserRes;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    EditText editNickname;
    Button btnRegister;
    TextView txtLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editNickname = findViewById(R.id.editNickname);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String nickname = editNickname.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty() || nickname.isEmpty()){
                    Toast.makeText(RegisterActivity.this,
                            "항목을 모두 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 이메일 형식이 맞는지 체크
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(pattern.matcher(email).matches() == false){
                    Toast.makeText(RegisterActivity.this,
                            "이메일을 정확히 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 길이 체크 4~12자까지만 허용
                if(password.length() < 4 ||  password.length() > 12  ){
                    Toast.makeText(RegisterActivity.this,
                            "비번 길이를 확인하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 네트워크로 회원가입 API 를 호출한다.

                // 1. retrofit 변수 생성
                Retrofit retrofit = NetworkClient.getRetrofitClient(RegisterActivity.this);

                // 2. api 패키지에 있는, Interface 생성
                UserApi api = retrofit.create(UserApi.class);

                // 3. 보낼 데이터 만든다. => 묶음처리 : 클래스의 객체 생성
                User user = new User(email, password, nickname);

                // 4. api 호출
                Call<UserRes> call = api.register(user);

                // 5. 서버로부터 받은 응답을 처리하는 코드 작성.
                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 서버에서 보낸 응답이 200 OK 일때 처리하는 코드

                        Log.i("AAA", "응답 code : "+response.code());

                        if(response.isSuccessful()){
                            UserRes userRes = response.body();

                            Log.i("AAA","result : " + userRes.result);
                            Log.i("AAA","accessToken : " + userRes.accessToken);

                            SharedPreferences sp =
                                    getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("token", userRes.accessToken);
                            editor.apply();

                            // 이상없으므로, 메인 액티비티를 실행한다.
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{

                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        // 유저한테 네트워크 통신 실패했다고 알려준다.
                    }
                });



            }
        });
    }
}



