package com.example.aristide.flowerproject.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.controller.Adapter;
import com.example.aristide.flowerproject.model.Plant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickCallback  {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    public static final int ADD_PLANT_ACTIVITY = 2;
    public static final int EDIT_PLANT_ACTIVITY = 3;
    public static final int SET_CURRENT_DATE_ACTIVITY = 4;

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Plant> listFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        recyclerView = (RecyclerView)findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(this);
        listFlowers = adapter.getPlants();

        if(listFlowers.size() == 0){
            Toast.makeText(getApplicationContext(), "Aucune plante", Toast.LENGTH_LONG).show();
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
     * 10 randoms plants
     * @throws Exception
     */
    private void generatePlantToList() throws Exception {
        String[] titles = {"Tulipe", "Rose", "Herbe", "Basilic", "Bleuet", "Roquette", "Trèfle", "Jasmine", "Lavande", "Lilas"};

        for(int i=0; i<10; i++){
            Plant plant = adapter.insertPlant(titles[i], i+1);
            adapter.notifyItemInserted(listFlowers.indexOf(plant));
        }
    }


    /**
     * Short click on Recyclerview
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
        extras.putString("CURRENTDATE", adapter.getCurrentDate());
        intent.putExtra(BUNDLE_EXTRAS, extras);

        startActivityForResult(intent, EDIT_PLANT_ACTIVITY);
    }

    /**
     *  Long click on RecyclerView
     *  Change the state of the plant and new last Date of watering
     * @param position of the item in the list
     */
    public void onLongListItemClick(int position) {
        Plant plant = listFlowers.get(position);
        if(plant.getState() == 1){
            Toast.makeText(getApplicationContext(), "Impossible de remettre à 0. Plante déjà O.K", Toast.LENGTH_LONG).show();
        }else{
            try {
                adapter.update(position, plant.getName(), plant.getDays(), new Date().toString(), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.notifyItemChanged(position);
        }
    }

    /**
     *
     * @param menu selected
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *  Actions about items on menu
     * @param menu selected
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
            case R.id.change_date:
                Intent i = new Intent(getApplicationContext(), CurrentDateActivity.class);

                startActivityForResult(i, SET_CURRENT_DATE_ACTIVITY);
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    /**
     * To read the result from newly created activity
     * @param requestCode send
     * @param resultCode receive
     * @param intent to coordinate between activities functions
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == EDIT_PLANT_ACTIVITY){

            String name = intent.getStringExtra("NAME");
            String date = intent.getStringExtra("DATE");
            int frequency = intent.getIntExtra("DAYS", 0);
            int state = intent.getIntExtra("STATE", 0);
            int id = intent.getIntExtra("ID", 0);
            try {
                listFlowers.get(id).setName(name);
                listFlowers.get(id).setDays(frequency);
                listFlowers.get(id).setDate(date);
                listFlowers.get(id).setState(state);

                adapter.update(id, name, frequency, date, state);
                adapter.notifyItemChanged(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(resultCode == ADD_PLANT_ACTIVITY){
            String name = intent.getStringExtra("NAME");
            int frequency = intent.getIntExtra("DAYS", 0);
            try {
                Plant plant = adapter.insertPlant(name, frequency);
                adapter.notifyItemInserted(listFlowers.indexOf(plant));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(resultCode == SET_CURRENT_DATE_ACTIVITY){
            adapter.setCurrentDate(intent.getStringExtra("DATE"));
            for(int i = 0; i<listFlowers.size(); i++){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date;
                try {
                    date = sdf.parse(listFlowers.get(i).getDate());
                    if(date.before(sdf.parse(adapter.getCurrentDate()))){
                        listFlowers.get(i).setState(Adapter._LATE);
                    }else if(date.equals(sdf.parse(adapter.getCurrentDate()))){
                        listFlowers.get(i).setState(Adapter._WARNING);
                    }else{
                        listFlowers.get(i).setState(Adapter._GOOD);
                    }
                    adapter.notifyItemChanged(i);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
