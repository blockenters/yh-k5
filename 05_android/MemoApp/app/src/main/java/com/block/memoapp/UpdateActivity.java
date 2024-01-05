package com.block.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.block.memoapp.api.MemoApi;
import com.block.memoapp.api.NetworkClient;
import com.block.memoapp.config.Config;
import com.block.memoapp.model.Memo;
import com.block.memoapp.model.Res;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnDate;
    Button btnTime;
    Button btnSave;

    String date;
    String time;

    int memoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // 받아오는 코드작성.
        Memo memo = (Memo) getIntent().getSerializableExtra("memo");
        memoId = memo.id;

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnSave = findViewById(R.id.btnSave);

        editTitle.setText(memo.title);
        editContent.setText(memo.content);

        // memo.date 에는 아래처럼 데이터가 들어있다.
        // "2023-08-03T11:30:00"
        String[] dateArray = memo.date.split("T");

        date = dateArray[0];
        time = dateArray[1].substring(0, 4+1);

        btnDate.setText(date);
        btnTime.setText(time);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                                int month = i1 + 1;
                                String strMonth;
                                if(month < 10){
                                    strMonth = "0" + month;
                                }else{
                                    strMonth = "" + month;
                                }

                                String strDay;
                                if(i2 < 10){
                                    strDay = "0" + i2;
                                }else{
                                    strDay = "" + i2;
                                }

                                date = i + "-" + strMonth + "-" + strDay;
                                btnDate.setText(date);

                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(
                        UpdateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String strHour;
                                if( i < 10){
                                    strHour = "0" + i;
                                }else {
                                    strHour = "" + i;
                                }

                                String strMin;
                                if( i1 < 10 ){
                                    strMin = "0" + i1;
                                }else{
                                    strMin = "" + i1;
                                }

                                time = strHour + ":" + strMin;
                                btnTime.setText(time);

                            }
                        },
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        true
                );
                dialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();
                String datetime = date + " " + time;

                if(title.isEmpty() || content.isEmpty() || date.isEmpty() || time.isEmpty()){
                    Toast.makeText(UpdateActivity.this,
                            "항목을 모두 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 네트워크 통해서 API 를 호출한다.
                showProgress();

                Retrofit retrofit = NetworkClient.getRetrofitClient(UpdateActivity.this);
                MemoApi api = retrofit.create(MemoApi.class);

                SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                String token = sp.getString("token", "");
                token = "Bearer " + token;

                Memo memo = new Memo(title, datetime, content);

                Call<Res> call = api.updateMemo(memoId, token, memo);

                call.enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Call<Res> call, Response<Res> response) {
                        dismissProgress();

                        if(response.isSuccessful()){

                            finish();

                        } else{

                        }

                    }

                    @Override
                    public void onFailure(Call<Res> call, Throwable t) {
                        dismissProgress();

                    }
                });


            }
        });

    }


    Dialog dialog;

    private void showProgress(){
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(new ProgressBar(this));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void dismissProgress(){
        dialog.dismiss();
    }

}



