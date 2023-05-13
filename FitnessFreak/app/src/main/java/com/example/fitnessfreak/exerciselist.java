package com.example.fitnessfreak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class exerciselist extends AppCompatActivity {

    private ListView lExercise;
    private ArrayList<Exercise> exercises;
    private CustomExercisedapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciselist);
        lExercise = findViewById(R.id.listview);
        loadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //addData();
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void loadData(){
        exercises = new ArrayList<>();
        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }

        while (rows.moveToNext()) {
            String types = db.getValueByKey("userinfo");
            String type[] = types.split("--");
            String key = rows.getString(0);
            String eventData = rows.getString(1);
            if(key.equalsIgnoreCase("userinfo")) continue;
            String[] fieldValues = eventData.split("---");
            String name = fieldValues[0];
            String time = fieldValues[1];
            String det = fieldValues[2];
            String img = fieldValues[3];
            //System.out.println(name);
            if((type[3].equals("Fatloss") && Integer.parseInt(key)%2==0) || (!type[3].equals("Fatloss") && Integer.parseInt(key)%2!=0)){
                Exercise e = new Exercise(key, name, det,time,img);
                exercises.add(e);
            }

        }
        db.close();
        adapter = new CustomExercisedapter(this, exercises);
        lExercise.setAdapter(adapter);
// handle the click on an event-list item
        lExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int
                    position, long id) {
                System.out.println("hola");
                Intent i = new Intent(exerciselist.this,ShowExercise.class);
                String value = db.getValueByKey(exercises.get(position).key);
                String values[] = value.split("---");
                System.out.println(value);
                i.putExtra("Exercise_key", exercises.get(position).key);

                startActivity(i);
            }
        });
// handle the long-click on an event-list item

    }




}

