package com.example.fitnessfreak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class planandinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planandinfo);
        findViewById(R.id.update).setOnClickListener(v -> {
            startActivity(new Intent(this, information.class));
        });

        findViewById(R.id.workout).setOnClickListener(v -> {
            startActivity(new Intent(this, exerciselist.class));
        });

        findViewById(R.id.mealplan).setOnClickListener(v -> {
            startActivity(new Intent(this, mealplan.class));
        });
    }
}

