package com.example.aristide.flowerproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aristide.flowerproject.R;

import static android.R.attr.id;

/**
 *
 */
public class AddPlantActitvy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Ajouter une plante");

        Button addItem = (Button) findViewById(R.id.btn_add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.editTextPlainTextInput)).getText().toString();
                String fequency = ((EditText) findViewById(R.id.editTextNumberInput)).getText().toString();
                if(name.isEmpty() || fequency.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Incomplet", Toast.LENGTH_LONG).show();
                }else{
                    Intent i = new Intent();
                    i.putExtra("NAME", ((EditText) findViewById(R.id.editTextPlainTextInput)).getText().toString());
                    i.putExtra("DAYS", Integer.valueOf(((EditText) findViewById(R.id.editTextNumberInput)).getText().toString()));
                    i.putExtra("ID", id);
                    setResult(MainActivity.ADD_PLANT_ACTIVITY, i);
                    finish();
                }
            }
        });
    }
}
