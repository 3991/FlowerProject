package com.example.aristide.flowerproject.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.example.aristide.flowerproject.R;
import java.util.Calendar;


public class CurrentDateActivity extends AppCompatActivity {

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_date);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Modifier la date");


        dateView = (TextView) findViewById(R.id.textView_date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Button modifPlant = (Button) findViewById(R.id.btn_edit_plant);
        modifPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent();
                    i.putExtra("DATE", dateView.getText().toString());

                    setResult(MainActivity.SET_CURRENT_DATE_ACTIVITY, i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }



    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            showDate(year, month+1, day);
        }
    };

    /**
     *
     * @param year concerned
     * @param month of the year
     * @param day of the month
     */
    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
