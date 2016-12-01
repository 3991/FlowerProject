package com.example.aristide.flowerproject.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aristide.flowerproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FlowerInformationsActivity extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_informations);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Une plante");

        final Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        //((TextView)findViewById(R.id.lbl_plant_name)).setText(extras.getString(EXTRA_QUOTE));
        ((TextView)findViewById(R.id.lbl_plant_day)).setText(extras.getString(EXTRA_ATTR));
        //((TextView)findViewById(R.id.lbl_plant_day)).getText()

        final EditText editTextPlainTextInput = (EditText) this.findViewById(R.id.editTextPlainTextInput);
        editTextPlainTextInput.setText(extras.getString("NAME"));

        final EditText editTextNumberInput = (EditText) this.findViewById(R.id.editTextNumberInput);
        editTextNumberInput.setText(extras.getString("DAYS"));

        final TextView editTextDateInput = (TextView) this.findViewById(R.id.textView_date);
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

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(year+"-"+month+"-"+day);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    cal.setTime(date);

                    if(cal.before(new Date())){
                        i.putExtra("STATE", 3);
                    }else if(cal.equals(new Date())){
                        i.putExtra("STATE", 1);
                    }else{
                        i.putExtra("STATE", 2);
                    }
                    setResult(MainActivity.EDIT_PLANT_ACTIVITY, i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dateView = (TextView) findViewById(R.id.textView_date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


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

    /**
     *
     */
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    showDate(year, month+1, day);
                }
            };

    /**
     *
     * @param year
     * @param month
     * @param day
     */
    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
