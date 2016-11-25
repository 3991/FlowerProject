package com.example.aristide.flowerproject.model;

import com.example.aristide.flowerproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by --- on 21/11/2016.
 */

public class ListFlowers {
    private static final String[] titles = {"Tulipe", "Rose", "Herbe"};

    public static List<Plant> getListFlowers(){
        List<Plant> data = new ArrayList<>();


        Plant item = new Plant();
        item.setImageResId(R.drawable.red_flower);

        item.setName(titles[1]);
        data.add(item);
        return data;
    }

}
