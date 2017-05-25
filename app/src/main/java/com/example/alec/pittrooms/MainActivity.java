package com.example.alec.pittrooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

    public void searchRooms(View view) {
        Intent intent = new Intent(this, DisplayResultsActivity.class);

//        EditText editBuilding = (EditText) findViewById(R.id.editBuilding);
//        EditText editDay = (EditText) findViewById(R.id.editDay);
//        EditText editTime = (EditText) findViewById(R.id.editTime);

//        String building = editBuilding.getText().toString();
        String building = "";
        String day = "";
        String time = "";
//        String day = editDay.getText().toString();
//        String time = editTime.getText().toString();

        intent.putExtra(EXTRA_BUILDING, building);
        intent.putExtra(EXTRA_DAY, day);
        intent.putExtra(EXTRA_TIME, time);
        startActivity(intent);
    }
}
