package com.example.alec.pittrooms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alec on 5/24/17.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private HashMap<String, String> buildings = new HashMap<>(23);

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<String> getRooms(String building, String day, String time) throws ParseException {
        building = parse_building(building);
        day = parse_day(day);

        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM sections WHERE building = \"" + building + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if(time_in_range(cursor.getString(2), time) && day_matches(cursor.getString(1), day))
                list.add(cursor.getString(4));
            else
                list.add("NOT " + cursor.getString(4));

            cursor.moveToNext();

        }
        cursor.close();
        return list;
    }

    private String parse_day(String day) {
        if ("monday".contains(day.toLowerCase()))
            return "Mo";
        else if ("tuesday".contains(day.toLowerCase()))
            return "Tu";
        else if ("wednesday".contains(day.toLowerCase()))
            return "We";
        else if ("thursday".contains(day.toLowerCase()))
            return "Th";
        else if ("friday".contains(day.toLowerCase()))
            return "Fr";
        else if ("saturday".contains(day.toLowerCase()))
            return "Sa";
        else if ("sunday".contains(day.toLowerCase()))
            return "Su";
        else
            return null;
    }

    private String parse_building(String building) {
        if (buildings.containsKey(building))
            return buildings.get(building);
        else if (buildings.containsValue(building))
            return building;
        else
            return null;
    }

    void init_building_map() {
        buildings.put("Allen Hall", "ALLEN");
        buildings.put("Alumni Hall", "ALUM");
        buildings.put("Bellefield Hall", "BELLH");
        buildings.put("Biomedical Science Tower", "BSTWR");
        buildings.put("Chevron Science Center", "CHVRN");
        buildings.put("Cathedral of Learning", "CL");
        buildings.put("Clapp Hall", "CLAPP");
        buildings.put("Crawford Hall", "CRAWF");
        buildings.put("Eberly Hall", "EBERL");
        buildings.put("Falk School", "FALKS");
        buildings.put("Pittsburgh Filmmakers", "FILM");
        buildings.put("Frick Fine Arts Building", "FKART");
        buildings.put("Langley Hall", "LANGY");
        buildings.put("Lawrence Hall", "LAWRN");
        buildings.put("Mervis Hall", "MERVS");
        buildings.put("Music Building", "MUSIC");
        buildings.put("Old Engineering Hall", "OEH");
        buildings.put("Public Health", "PUBHL");
        buildings.put("Sennott Square", "SENSQ");
        buildings.put("Thackeray Hall", "THACK");
        buildings.put("Thaw Hall", "THAW");
        buildings.put("Trees Hall", "TREES");
        buildings.put("Wesley W. Posvar Hall", "WWPH");
    }

    private boolean day_matches(String days, String input) {
        return days.toLowerCase().contains(input.toLowerCase());
    }
    private boolean time_in_range(String range, String input) throws ParseException {
        String[] times = range.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date start = sdf.parse(convert_to_24hr(times[0]));
        Date end = sdf.parse(convert_to_24hr(times[1]));
        Date now = new Date();
        if (input != null)
            now = sdf.parse(input);

        return (start.getTime() < now.getTime()) && (now.getTime() < end.getTime());
    }

    private String convert_to_24hr(String time) {
        String am_pm;
        if (time.toLowerCase().contains("pm"))
            am_pm = "PM";
        else
            am_pm = "AM";

        String[] split_time = time.split(" ");
        split_time = split_time[0].split(":");

        int hour = Integer.parseInt(split_time[0]);
        int min = 00;
        if (split_time.length > 1)
            min = Integer.parseInt(split_time[1]);

        if (am_pm == "PM" && hour < 12) hour += 12;

        return Integer.toString(hour) + ":" + Integer.toString(min);
    }
}
