package com.example.aristide.flowerproject.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.DataBase.DataBase;
import com.example.aristide.flowerproject.model.Plant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class between the data and the view
 */
public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private List<Plant> listPlants;
    private LayoutInflater inflater;
    private DataBase database;
    public static final int _ERROR = -1;
    public static final int _GOOD = 1;
    public static final int _WARNING = 2;
    public static final int _LATE = 3;
    private String currentDate;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int position);
        void onLongListItemClick(int position);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public Adapter(Context context){
        this.inflater = LayoutInflater.from(context);
        database = new DataBase(context);

        this.listPlants = database.getFlowers();
        setCurrentDate(getDateTime());
    }

    public List<Plant> getPlants(){
        return this.listPlants;
    }


    @Override
    public Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Plant item = listPlants.get(position);
        holder.name.setText(item.getName());
        holder.days.setText(String.valueOf(item.getDays()));
        holder.icon_flower.setImageResource(item.getImageResId());
        if(item.getState() == _LATE){
            holder.im_icon_state.setImageResource(R.drawable.warning_red_icon);
        }else if(item.getState() == _WARNING){
            holder.im_icon_state.setImageResource(R.drawable.warning_orange_icon);
        }else{
            holder.im_icon_state.setImageResource(R.drawable.check_icon);
        }
    }

    @Override
    public int getItemCount() {
        return listPlants.size();
    }


    /**
     *  To call DB to insert a new plant
     * @param name of the plant
     * @param frequency last date of watering
     * @return item inserted
     * @throws Exception
     */
    public Plant insertPlant(String name, int frequency) throws Exception {
        Plant item = new Plant();
        String date = getDateTime();
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Calendar cal = Calendar.getInstance();
        if(database.insertPlant(name, frequency, date, Adapter._GOOD) != _ERROR){
            item.setImageResId(R.drawable.red_flower);
            item.setName(name);
            item.setDays(frequency);
            item.setDate(date);
            item.setState(Adapter._GOOD);
            listPlants.add(item);
        }else{
            throw new Exception("Error to put a new plant");
        }
        return item;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * Updates a row in the database
     * @param id of the plant
     * @param name The new name value
     * @param frequency The new frequency value
     */
    public void update(int id, String name, int frequency, String date, int state) throws Exception {
        if(database.updatePlant(id, name, frequency, date, state) != _ERROR){
            listPlants.get(id).setName(name);
            listPlants.get(id).setDays(frequency);
            listPlants.get(id).setDate(date);
            listPlants.get(id).setState(state);
        }else{
            throw new Exception("Error to update a new plant");
        }
    }

    /**
     * Delete a plant in the database
     * @param id of the plant
     */
    public void deletePlant(int id){
        database.delete(id);
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView name;
        private TextView days;
        private ImageView icon_flower;
        private ImageView im_icon_state;
        private View container;

        public Holder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.lbl_text_name);
            days = (TextView)itemView.findViewById(R.id.lbl_text_days);
            icon_flower = (ImageView)itemView.findViewById(R.id.im_icon_flower);
            im_icon_state = (ImageView)itemView.findViewById(R.id.im_icon_state);

            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
            container.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view){
            if(view.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(view.getId() == R.id.cont_item_root){
                itemClickCallback.onLongListItemClick(getAdapterPosition());

                return true;
            }
            return false;
        }
    }
}
