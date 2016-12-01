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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int position);
        void onLongListItemClick(int position);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public Adapter(List<Plant> listPlants, Context context){
        this.inflater = LayoutInflater.from(context);

        database = new DataBase(context);

        this.listPlants = database.getFlowers();
    }

    public void init() throws Exception {
        for(Plant ob : listPlants){
            insertPlant(ob.getName(), ob.getDays(), ob.getImageState());
        }
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
    }

    @Override
    public int getItemCount() {
        return listPlants.size();
    }


    /**
     *  To call DB to insert a new plant
     * @param name
     * @param frequency
     * @return
     * @throws Exception
     */
    public Plant insertPlant(String name, int frequency, int state) throws Exception {
        Plant item = new Plant();
        String date = getDateTime();
        if(database.insertPlant(name, frequency, date, state) != _ERROR){
            item.setImageResId(R.drawable.red_flower);
            item.setName(name);
            item.setDays(frequency);
            item.setDate(date);
            if(state == 1){
                item.setImageState(R.drawable.check_icon);
            }else if(state == 2){
                item.setImageState(R.drawable.warning_orange_icon);
            }else{
                item.setImageState(R.drawable.warning_red_icon);
            }
            listPlants.add(item);
        }else{
            throw new Exception("Error to put a new plant");
        }
        return item;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Updates a row in the database
     * @param id
     * @param name The new name value
     * @param frequency The new frequency value
     */
    public void update(int id, String name, int frequency, String date, int state) throws Exception {
        if(database.updatePlant(id, name, frequency, date, state) != _ERROR){
            listPlants.get(id).setName(name);
            listPlants.get(id).setDays(frequency);
            listPlants.get(id).setDate(date);

            if(state == 1){
                listPlants.get(id).setImageState(R.drawable.check_icon);
            }else if(state == 2){
                listPlants.get(id).setImageState(R.drawable.warning_orange_icon);
            }else{
                listPlants.get(id).setImageState(R.drawable.warning_red_icon);
            }
        }else{
            throw new Exception("Error to update a new plant");
        }
    }

    /**
     *
     * @param id
     */
    public void deletePlant(int id){
        database.delete(id);
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView name;
        private TextView days;
        private ImageView icon_flower;
        private View container;

        public Holder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.lbl_text_name);
            days = (TextView)itemView.findViewById(R.id.lbl_text_days);
            icon_flower = (ImageView)itemView.findViewById(R.id.im_icon_flower);

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
