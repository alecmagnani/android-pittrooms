package com.example.alec.pittrooms;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayResultsActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        // Get the Intent that started this activity and extract the strings
        Intent intent = getIntent();
        String building = intent.getStringExtra(MainActivity.EXTRA_BUILDING);
        String day = intent.getStringExtra(MainActivity.EXTRA_DAY);
        String time = intent.getStringExtra(MainActivity.EXTRA_TIME);

        this.listView = (ListView) findViewById(R.id.listview);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.init_building_map();
        databaseAccess.open();
        List<String> rooms = null;
        try {
            rooms = databaseAccess.getRooms(building, day, time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rooms);
        this.listView.setAdapter(adapter);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
