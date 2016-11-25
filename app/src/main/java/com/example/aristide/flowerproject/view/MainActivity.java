package com.example.aristide.flowerproject.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.controller.Adapter;
import com.example.aristide.flowerproject.model.Flower;
import com.example.aristide.flowerproject.model.ListFlowers;

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

        listData = (ArrayList) Flower.getListData();

        recyclerView = (RecyclerView)findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(listData, this);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Button addItem = (Button) findViewById(R.id.btn_add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToList();
            }
        });
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }

    private void addItemToList() {
        ListFlowers item = Flower.getRandomListItem();
        listData.add(item);
        adapter.notifyItemInserted(listData.indexOf(item));
    }

    private void deleteItem(final int position) {
        listData.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemClick(int p) {
        ListFlowers item = (ListFlowers) listData.get(p);

        Intent i = new Intent(this, FlowerInformationsActivity.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getName());
        //extras.putString(EXTRA_ATTR, item.getSubTitle());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }
}
