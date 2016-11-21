package com.example.aristide.flowerproject.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aristide.flowerproject.R;
import com.example.aristide.flowerproject.model.ListItem;

import java.util.List;

/**
 * Created by --- on 21/11/2016.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private List<ListItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public Adapter(List<ListItem> listData, Context context){
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ListItem item = listData.get(position);
        holder.name.setText(item.getName());
        holder.icon_flower.setImageResource(item.getImageResId());
        holder.icon_edit.setImageResource(item.getImageResId());
        holder.icon_del.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView days;
        private ImageView icon_flower;
        private ImageView icon_del;
        private ImageView icon_edit;
        private ImageView icon_see;
        private View container;

        public Holder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.lbl_text_name);
            days = (TextView)itemView.findViewById(R.id.lbl_text_days);
            icon_flower = (ImageView)itemView.findViewById(R.id.im_icon_flower);
            icon_del = (ImageView)itemView.findViewById(R.id.im_icon_del);
            icon_edit = (ImageView)itemView.findViewById(R.id.im_icon_edit);
            icon_see = (ImageView)itemView.findViewById(R.id.im_icon_edit);
            icon_see.setOnClickListener(this);
            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
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
