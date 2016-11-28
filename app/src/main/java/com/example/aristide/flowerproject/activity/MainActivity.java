package com.example.aristide.flowerproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.controller.Adapter;
import com.example.aristide.flowerproject.model.ListFlowers;
import com.example.aristide.flowerproject.model.Plant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickCallback  {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";
    public static final int ADD_PLANT_ACTIVITY = 2;
    public static final int EDIT_PLANT_ACTIVITY = 3;

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList listFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        listFlowers = new ArrayList();

        recyclerView = (RecyclerView)findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(listFlowers, this);
        try {
            adapter.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * To add a swipe.
     * It works with a RecyclerView and a Callback class.
     * @return
     */
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

    private void addPlantToList() throws Exception {
        Plant plant = adapter.addPlant("testg",12);
        adapter.notifyItemInserted(listFlowers.indexOf(plant));
    }

    private void generatePlantToList() throws Exception {
        String[] titles = {"Tulipe", "Rose", "Herbe", "Basilic", "Bleuet", "Roquette", "Trèfle", "Jasmine", "Lavande", "Lilas"};
        for(int i=0; i<10; i++){
            Plant plant = adapter.addPlant(titles[i],i);
            adapter.notifyItemInserted(listFlowers.indexOf(plant));
        }
    }

    private void deleteItem(final int position) {
        listFlowers.remove(position);
        adapter.notifyItemRemoved(position);
    }

    /**
     * recyclerview
     * @param p
     */
    @Override
    public void onItemClick(int p) {
        Plant item = (Plant) listFlowers.get(p);

        Intent intent = new Intent(getApplicationContext(), FlowerInformationsActivity.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getName());
        extras.putString(EXTRA_ATTR, String.valueOf(item.getDays()));

        intent.putExtra(BUNDLE_EXTRAS, extras);

        //startActivity(i);

        startActivityForResult(intent, EDIT_PLANT_ACTIVITY);
    }

    /**
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {

        switch (menu.getItemId()) {
            case R.id.add_menu_button:
                //try {
                    adapter.test();
                    /*Intent i = new Intent(getApplicationContext(), AddPlantActitvy.class);

                    startActivityForResult(i, ADD_PLANT_ACTIVITY);*/
                //} catch (Exception e) {
                   //e.printStackTrace();
                //}
                return true;
            case R.id.generate_menu_button:
                try {
                    generatePlantToList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    /**
     * To read the result from newly created activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == EDIT_PLANT_ACTIVITY){
            String result = data.getStringExtra("name");
            Log.d("HOLA", result+"ggg");
        }
    }
}
