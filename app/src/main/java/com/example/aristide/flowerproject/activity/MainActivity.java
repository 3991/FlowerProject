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
import com.example.aristide.flowerproject.model.Plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//SUPP en DB dans SWIPE
//gestion de l'icone en fonction de la date

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickCallback  {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    public static final int ADD_PLANT_ACTIVITY = 2;
    public static final int EDIT_PLANT_ACTIVITY = 3;

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Plant> listFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //listFlowers = new ArrayList();

        recyclerView = (RecyclerView)findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(this);
        listFlowers = adapter.getPlants();

        try {
            //adapter.init();
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
     * @return ItemTouchHelper.Callback
     */
    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView1, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                listFlowers.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
    }

    /**
     *
     * @throws Exception
     */
    private void generatePlantToList() throws Exception {
        String[] titles = {"Tulipe", "Rose", "Herbe", "Basilic", "Bleuet", "Roquette", "Trèfle", "Jasmine", "Lavande", "Lilas"};
        for(int i=0; i<10; i++){
            Plant plant = adapter.insertPlant(titles[i], i, 2);
            adapter.notifyItemInserted(listFlowers.indexOf(plant));
        }
    }


    /**
     * Recyclerview
     * @param position is the position of the plant in the list
     */
    @Override
    public void onItemClick(int position) {
        Plant item = listFlowers.get(position);

        Intent intent = new Intent(getApplicationContext(), FlowerInformationsActivity.class);

        Bundle extras = new Bundle();
        extras.putInt("ID", position);
        extras.putString("NAME", item.getName());
        extras.putString("DAYS", String.valueOf(item.getDays()));
        extras.putString("DATE", item.getDate());
        intent.putExtra(BUNDLE_EXTRAS, extras);

        startActivityForResult(intent, EDIT_PLANT_ACTIVITY);
    }

    /**
     *  Changement of the state of the plant
     *  And new last Date of watering
     * @param position
     */
    public void onLongListItemClick(int position) {
        Plant plant = listFlowers.get(position);

        try {
            adapter.update(position, plant.getName(), plant.getDays(), new Date().toString(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.notifyItemChanged(position);
    }

    /**
     *
     * @param menu
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {

        switch (menu.getItemId()) {
            case R.id.add_menu_button:
                Intent intent = new Intent(getApplicationContext(), AddPlantActitvy.class);

                startActivityForResult(intent, ADD_PLANT_ACTIVITY);
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
            String name = data.getStringExtra("NAME");
            String date = data.getStringExtra("DATE");
            int frequency = data.getIntExtra("DAYS", 0);
            int state = data.getIntExtra("STATE", 0);
            int id = data.getIntExtra("ID", 0);
            try {
                adapter.update(id, name, frequency, date, state);
                adapter.notifyItemChanged(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(resultCode == ADD_PLANT_ACTIVITY){
            String name = data.getStringExtra("NAME");
            int frequency = data.getIntExtra("DAYS", 0);
            try {
                Plant plant = adapter.insertPlant(name, frequency, 1);
                adapter.notifyItemInserted(listFlowers.indexOf(plant));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
