package com.block.employer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.block.employer.adapter.EmployeeAdapter;
import com.block.employer.model.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ProgressBar progressBar;


    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    ArrayList<Employee> employeeArrayList = new ArrayList<>();


    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if ( o.getResultCode() == 100 ) {
                        Employee employee = (Employee) o.getData().getSerializableExtra("employee");
                        employeeArrayList.add(0, employee);
                        adapter.notifyDataSetChanged();
                    }else if(o.getResultCode() == 200){

                        int index = o.getData().getIntExtra("index", 0);
                        Employee employee = (Employee) o.getData().getSerializableExtra("employee");

                        employeeArrayList.set(index , employee);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("직원 리스트");

        btnAdd = findViewById(R.id.btnAdd);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                launcher.launch(intent);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://block1-image-test.s3.ap-northeast-2.amazonaws.com/employees.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONArray data = response.getJSONArray("data");

                            for(int i = 0; i < data.length(); i++ ){
                                JSONObject item = data.getJSONObject(i);
                                int id = item.getInt("id");
                                String name = item.getString("employee_name");
                                int salary = item.getInt("employee_salary");
                                int age = item.getInt("employee_age");
                                String profileImage=item.getString("profile_image");

                                Employee employee = new Employee(id, name, salary, age, profileImage);

                                employeeArrayList.add(employee);
                            }

                            // 화면에 표시
                            adapter = new EmployeeAdapter(MainActivity.this, employeeArrayList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            // 유저한테 알린다.

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


    // 액션바의 메뉴 아이콘이 나오도록 설정하는 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    // 액션바의 메뉴 아이콘 클릭하면, 동작하는 함수
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if( item.getItemId() == R.id.menuAdd ){

            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            launcher.launch(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}






