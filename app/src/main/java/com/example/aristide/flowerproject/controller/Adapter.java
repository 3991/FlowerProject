package com.example.aristide.flowerproject.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.model.DataBase;
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

    private static final int _ERROR = -1;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public Adapter(List<Plant> listPlants, Context context){
        this.inflater = LayoutInflater.from(context);
        this.listPlants = listPlants;
        database = new DataBase(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if(database.putPlant(name, days) != _ERROR){
            item.setImageResId(R.drawable.red_flower);
            item.setName(name);
            item.setDays(days);
            listPlants.add(item);
        }else{
            throw new Exception("Error to put a new plant");
        }
        return item;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getFlowers(){
        return database.getFlowers();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView days;
        private ImageView icon_flower;
        private View container;
        private Context context;

        public Holder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.lbl_text_name);
            days = (TextView)itemView.findViewById(R.id.lbl_text_days);
            icon_flower = (ImageView)itemView.findViewById(R.id.im_icon_flower);

            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
            this.context = context;
        }

        @Override
        public void onClick(View view){
            if(view.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
            }else{

            }
        }
    }
}
