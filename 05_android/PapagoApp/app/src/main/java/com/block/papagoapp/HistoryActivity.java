package com.block.papagoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.block.papagoapp.adapter.HistoryAdapter;
import com.block.papagoapp.model.History;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter adapter;
    ArrayList<History> historyArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyArrayList = (ArrayList<History>) getIntent().getSerializableExtra("historyArrayList");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

        adapter = new HistoryAdapter(HistoryActivity.this, historyArrayList);
        recyclerView.setAdapter(adapter);

    }
}