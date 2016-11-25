package com.example.aristide.flowerproject.model;

import com.example.aristide.flowerproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by --- on 21/11/2016.
 */

public class Flower {
    private static final String[] titles = {"Tulipe", "Rose", "Herbe"};
    private static final int[] icons = {android.R.drawable.ic_popup_reminder,
                                        android.R.drawable.ic_menu_add,
                                        android.R.drawable.ic_menu_delete};

    public static List<ListFlowers> getListData(){
        List<ListFlowers> data = new ArrayList<>();

        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable
        //RecyclerView
        /*for (int x = 0; x < 4; x++) {
            //create ListFlowers with dummy data, then add them to our List
            for (int i = 0; (i < titles.length) && (i < icons.length); i++) {
                ListFlowers item = new ListFlowers();
                item.setImageResId(R.drawable.red_flower);
                //item.setImageResId(icons[i]);
                item.setName(titles[i]);
                data.add(item);
            }
        }*/
        ListFlowers item = new ListFlowers();
        item.setImageResId(R.drawable.red_flower);
        //item.setImageResId(icons[i]);
        item.setName(titles[1]);
        data.add(item);
        return data;
    }

    public static ListFlowers getRandomListItem(){
        int rand = new Random().nextInt(3);

        ListFlowers item = new ListFlowers();

        item.setName(titles[rand]);

        return item;
    }
}
