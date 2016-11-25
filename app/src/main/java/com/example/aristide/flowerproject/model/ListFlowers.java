package com.example.aristide.flowerproject.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.aristide.flowerproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.security.AccessController.getContext;

/**
 * Created by --- on 21/11/2016.
 */

public class ListFlowers {
    private static final String[] titles = {"Tulipe", "Rose", "Herbe"};

    public static List<Flower> getListFlowers(){
        List<Flower> data = new ArrayList<>();

        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable
        //RecyclerView
        /*for (int x = 0; x < 4; x++) {
            //create Flower with dummy data, then add them to our List
            for (int i = 0; (i < titles.length) && (i < icons.length); i++) {
                Flower item = new Flower();
                item.setImageResId(R.drawable.red_flower);
                //item.setImageResId(icons[i]);
                item.setName(titles[i]);
                data.add(item);
            }
        }*/
        Flower item = new Flower();
        item.setImageResId(R.drawable.red_flower);

        item.setName(titles[1]);
        data.add(item);
        return data;
    }

    public static Flower getRandomListItem(){
        int rand = new Random().nextInt(3);

        Flower item = new Flower();

        item.setName(titles[rand]);

        return item;
    }
}
