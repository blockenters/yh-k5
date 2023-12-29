package com.block.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.block.employer.model.Employee;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editSalary;
    EditText editAge;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        
        getSupportActionBar().setTitle("직원 추가");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.editName);
        editSalary = findViewById(R.id.editSalary);
        editAge = findViewById(R.id.editAge);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String strSalary = editSalary.getText().toString().trim();
                String strAge = editAge.getText().toString().trim();

                if(name.isEmpty() || strSalary.isEmpty() || strAge.isEmpty()){
                    Toast.makeText(AddActivity.this,
                            "필수항목을 모두 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int salary = Integer.parseInt(strSalary);
                int age = Integer.parseInt(strAge);

                Employee employee = new Employee(name, salary, age);

                Intent intent = new Intent();
                intent.putExtra("employee", employee);
                setResult(100, intent);

                finish();

            }
        });

    }

    // 화살표 버튼 누르면 동작하는 함수.
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuSave){


            String name = editName.getText().toString().trim();
            String strSalary = editSalary.getText().toString().trim();
            String strAge = editAge.getText().toString().trim();

            if(name.isEmpty() || strSalary.isEmpty() || strAge.isEmpty()){
                Toast.makeText(AddActivity.this,
                        "필수항목을 모두 입력하세요.",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            int salary = Integer.parseInt(strSalary);
            int age = Integer.parseInt(strAge);

            Employee employee = new Employee(name, salary, age);

            Intent intent = new Intent();
            intent.putExtra("employee", employee);
            setResult(100, intent);

            finish();


        }

        return super.onOptionsItemSelected(item);
    }
}











