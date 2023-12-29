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
import android.widget.TextView;
import android.widget.Toast;

import com.block.employer.model.Employee;

public class UpdateActivity extends AppCompatActivity {

    TextView txtName;
    EditText editAge;
    EditText editSalary;
    Button btnSave;


    Employee employee;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setTitle("직원 수정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = findViewById(R.id.txtName);
        editAge = findViewById(R.id.editAge);
        editSalary = findViewById(R.id.editSalary);
        btnSave = findViewById(R.id.btnSave);

        // 선택한 사람의 정보를 화면에 먼저 보여준다.
        index = getIntent().getIntExtra("index", 0);

        employee = (Employee) getIntent().getSerializableExtra("employee");

        txtName.setText(employee.name);
        editAge.setText(""+employee.age);
        editSalary.setText(""+employee.salary);

        // 수정 버튼 누르면, 수정된 데이터를 메인액티비티에 돌려준다.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAge = editAge.getText().toString().trim();
                String strSalary = editSalary.getText().toString().trim();

                if(strAge.isEmpty() || strSalary.isEmpty()){
                    Toast.makeText(UpdateActivity.this,
                            "필수항목 모두 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(strAge);
                int salary = Integer.parseInt(strSalary);

                employee.age =  age;
                employee.salary =  salary;

                Intent intent = new Intent();
                intent.putExtra("index", index);
                intent.putExtra("employee", employee);
                setResult(200, intent);

                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuSave){

            String strAge = editAge.getText().toString().trim();
            String strSalary = editSalary.getText().toString().trim();

            if(strAge.isEmpty() || strSalary.isEmpty()){
                Toast.makeText(UpdateActivity.this,
                        "필수항목 모두 입력하세요.",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            int age = Integer.parseInt(strAge);
            int salary = Integer.parseInt(strSalary);

            employee.age =  age;
            employee.salary =  salary;

            Intent intent = new Intent();
            intent.putExtra("index", index);
            intent.putExtra("employee", employee);
            setResult(200, intent);

            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}



