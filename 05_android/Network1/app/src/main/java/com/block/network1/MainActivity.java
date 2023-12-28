package com.block.network1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    TextView txtUserId;
    TextView txtId;
    TextView txtTitle;
    TextView txtBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserId = findViewById(R.id.txtUserId);
        txtId = findViewById(R.id.txtId);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);

        // Volley 로 네트워크 통신해서 데이터 받아온다.
        // 1. 네트워크 통신에 필요한 큐 를 만든다.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // 2. 리퀘스트를 만든다.
        //    중요! 리스판스의 형태를 확인!
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.i("AAA", response.toString());

                        try {
                            int userId = response.getJSONObject(0).getInt("userId");
                            int id = response.getJSONObject(0).getInt("id");
                            String title = response.getJSONObject(0).getString("title");
                            String body = response.getJSONObject(0).getString("body");

                            txtUserId.setText(""+userId);
                            txtId.setText(""+id);
                            txtTitle.setText(title);
                            txtBody.setText(body);

                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this,
                                    "네트워크 파싱 에러입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        // 3. 큐에 위의 리퀘스트를 넣는다. 그러면 실행된다.
        queue.add(request);

    }
}




