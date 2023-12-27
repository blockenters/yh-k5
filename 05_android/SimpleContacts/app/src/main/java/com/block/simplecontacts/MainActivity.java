package com.block.simplecontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.block.simplecontacts.adapter.ContactAdapter;
import com.block.simplecontacts.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    Button btnSave;

    // 리사이클러뷰는, 쌍으로 같이 다니는 애들이 있다.
    // 나중에 적는다.
    RecyclerView recyclerView;
    ContactAdapter adapter;
    ArrayList<Contact> contactArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);
        // 리사이클러뷰는 같이쓰는 코드가 있으니까,
        // 나중에 작성한다.
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if(name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(MainActivity.this,
                            "이름과 폰번을 모두 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Contact contact = new Contact(name, phone);
                contactArrayList.add(contact);

                // 데이터가 바뀌면, 바뀌었다고 알려줘야
                // 화면 갱신이 된다.
                adapter.notifyDataSetChanged();

                editName.setText("");
                editPhone.setText("");
            }
        });

        // 어댑터 만들고, 화면에 적용.
        adapter = new ContactAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(adapter);
    }
}







