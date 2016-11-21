package com.example.aristide.flowerproject.model;

import com.example.aristide.flowerproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by --- on 21/11/2016.
 */

public class Data {
    private static final String[] titles = {"Tulipe", "Rose", "Herbe"};
    private static final int[] icons = {android.R.drawable.ic_popup_reminder,
                                        android.R.drawable.ic_menu_add,
                                        android.R.drawable.ic_menu_delete};

    public static List<ListItem> getListData(){
        List<ListItem> data = new ArrayList<>();

        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable
        //RecyclerView
        for (int x = 0; x < 4; x++) {
            //create ListItem with dummy data, then add them to our List
            for (int i = 0; (i < titles.length) && (i < icons.length); i++) {
                ListItem item = new ListItem();
                item.setImageResId(R.drawable.red_flower);
                //item.setImageResId(icons[i]);
                item.setName(titles[i]);
                data.add(item);
            }
        }
        return data;
    }
}
