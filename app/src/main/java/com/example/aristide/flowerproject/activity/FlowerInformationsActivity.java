package com.example.aristide.flowerproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aristide.flowerproject.R;

public class FlowerInformationsActivity extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_informations);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Une plante");

        final Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        //((TextView)findViewById(R.id.lbl_plant_name)).setText(extras.getString(EXTRA_QUOTE));
        //((TextView)findViewById(R.id.lbl_plant_day)).setText(extras.getString(EXTRA_ATTR));


        final EditText editTextPlainTextInput = (EditText) this.findViewById(R.id.editTextPlainTextInput);
        editTextPlainTextInput.setText(extras.getString("NAME"));

        final EditText editTextNumberInput = (EditText) this.findViewById(R.id.editTextNumberInput);
        editTextNumberInput.setText(extras.getString("DAYS"));

        final EditText editTextDateInput = (EditText) this.findViewById(R.id.editTextDateInput);
        editTextDateInput.setText(extras.getString("DATE"));

        final int id = extras.getInt("ID");

        Button modifPlant = (Button) findViewById(R.id.btn_edit_plant);
        modifPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent();
                    i.putExtra("NAME", editTextPlainTextInput.getText().toString());
                    i.putExtra("DAYS", Integer.valueOf(editTextNumberInput.getText().toString()));
                    i.putExtra("ID", id);
                    setResult(MainActivity.EDIT_PLANT_ACTIVITY, i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
