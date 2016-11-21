package com.example.aristide.flowerproject.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.controller.Adapter;
import com.example.aristide.flowerproject.model.Data;
import com.example.aristide.flowerproject.model.ListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickCallback  {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listData = (ArrayList) Data.getListData();

        recyclerView = (RecyclerView)findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(Data.getListData(), this);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
    }

    @Override
    public void onItemClick(int p) {
        ListItem item = (ListItem) listData.get(p);

        Intent i = new Intent(this, FlowerInformationsActivity.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getName());
        //extras.putString(EXTRA_ATTR, item.getSubTitle());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }
}
