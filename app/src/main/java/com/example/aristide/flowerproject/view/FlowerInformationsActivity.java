package com.example.aristide.flowerproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aristide.flowerproject.R;

public class FlowerInformationsActivity extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_informations);

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        //((TextView)findViewById(R.id.lbl_text_name)).setText(extras.getString(EXTRA_QUOTE));
        //((TextView)findViewById(R.id.lbl_text_days)).setText(extras.getString(EXTRA_ATTR));
    }
}
