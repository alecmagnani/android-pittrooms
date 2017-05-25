package com.example.alec.pittrooms;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_BUILDING = "com.example.pittrooms.BUILDING";
    public static final String EXTRA_DAY = "com.example.pittrooms.DAY";
    public static final String EXTRA_TIME = "com.example.pittrooms.TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner buildingSpinner = (Spinner) findViewById(R.id.building_spinner);
        ArrayAdapter<CharSequence> building_adapter = ArrayAdapter.createFromResource(this,
                R.array.building_array, android.R.layout.simple_spinner_item);
        building_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(building_adapter);

        Spinner daySpinner = (Spinner) findViewById(R.id.day_spinner);
        ArrayAdapter<CharSequence> day_adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(day_adapter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void searchRooms(View view) {
        Intent intent = new Intent(this, DisplayResultsActivity.class);

        Spinner buildingSpinner = (Spinner) findViewById(R.id.building_spinner);
        Spinner daySpinner = (Spinner) findViewById(R.id.day_spinner);
        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);

        String building = buildingSpinner.getSelectedItem().toString();
        String day = daySpinner.getSelectedItem().toString();
        String time = timePicker.getHour() + ":" + timePicker.getMinute();

        intent.putExtra(EXTRA_BUILDING, building);
        intent.putExtra(EXTRA_DAY, day);
        intent.putExtra(EXTRA_TIME, time);
        startActivity(intent);
    }
}
