package com.block.contacts;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.block.contacts.adapter.ContactAdapter;
import com.block.contacts.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    // 리사이클러뷰는 이따가 다른것들하고 함께.
    RecyclerView recyclerView;
    ContactAdapter adapter;
    ArrayList<Contact> contactArrayList = new ArrayList<>();


    // 애드액티비티로부터 데이터 받는 코드
    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {

                            if(o.getResultCode() == 100){
                                String name = o.getData().getStringExtra("name");
                                String phone = o.getData().getStringExtra("phone");

                                Log.i("AAA" , name + " , " + phone);

                                Contact contact = new Contact(name, phone);

                                contactArrayList.add(contact);

                                adapter.notifyDataSetChanged();

                            }

                        }
                    }) ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        // 리사이클러뷰는 여기.
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 연락처 추가하는 새 액티비티를 실행한다.
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                launcher.launch(intent);
            }
        });

        // 어댑터 만들고, 리사이클러뷰에 적용!
        adapter = new ContactAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(adapter);

    }
}


