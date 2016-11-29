package com.example.aristide.flowerproject.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.DataBase.DataBase;
import com.example.aristide.flowerproject.model.Plant;

import java.util.ArrayList;
import java.util.List;

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
        this.listPlants = listPlants;
        database = new DataBase(context);
    }

    public void init() throws Exception {
        for(Plant ob : listPlants){
            addPlant(ob.getName(), ob.getDays());
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
     *
     * @param name
     * @param days
     * @return
     * @throws Exception
     */
    public Plant addPlant(String name, int days) throws Exception {
        Plant item = new Plant();
        if(database.insertPlant(name, days) != _ERROR){
            item.setImageResId(R.drawable.red_flower);
            item.setName(name);
            item.setDays(days);
            listPlants.add(item);
        }else{
            throw new Exception("Error to put a new plant");
        }
        return item;
    }

    public void test(){
        String[] cv = database.selectPlant(3);
        Log.wtf("TAG", cv[0]);

    }

    /**
     * Updates a row in the database
     * @param id
     * @param name The new name value
     * @param days The new days value
     */
    public void update(int id, String name, int days) throws Exception {
        Plant item = new Plant();Log.d("CHANGEMENT","heo");
        if(database.updatePlant(id, name, days) != _ERROR){
            listPlants.get(id).setName(name);
            listPlants.get(id).setDays(days);
            Log.d("CHANGEMENT","ggg"+listPlants.get(2).getName());
        }else{Log.d("ERROR","ERREOR ???");
            throw new Exception("Error to put a new plant");
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getFlowers(){
        return database.getFlowers();
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
