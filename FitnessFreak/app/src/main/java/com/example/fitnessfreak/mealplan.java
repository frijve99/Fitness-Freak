package com.example.fitnessfreak;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class mealplan extends AppCompatActivity {
    private LinearLayout breakfast1,breakfast2,lunch1,lunch2,snaks1,snaks2,dinner1,dinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealplan);
        breakfast1=findViewById(R.id.meal1);
        breakfast2=findViewById(R.id.meal5);
        lunch1=findViewById(R.id.meal2);
        lunch2=findViewById(R.id.meal6);
        snaks1=findViewById(R.id.meal3);
        snaks2=findViewById(R.id.meal7);
        dinner1=findViewById(R.id.meal4);
        dinner2=findViewById(R.id.meal8);
    }

    protected void onStart() {
        super.onStart();

        KeyValueDB db = new KeyValueDB(this);
        String types = db.getValueByKey("userinfo");
        String type[] = types.split("--");
        if(type[3].contains("Muscle Build")){
            breakfast1.setVisibility(View.GONE);
            lunch1.setVisibility(View.GONE);
            snaks1.setVisibility(View.GONE);
            dinner1.setVisibility(View.GONE);

            breakfast2.setVisibility(View.VISIBLE);
            lunch2.setVisibility(View.VISIBLE);
            snaks2.setVisibility(View.VISIBLE);
            dinner2.setVisibility(View.VISIBLE);
        }
        else{
            breakfast1.setVisibility(View.VISIBLE);
            lunch1.setVisibility(View.VISIBLE);
            snaks1.setVisibility(View.VISIBLE);
            dinner1.setVisibility(View.VISIBLE);

            breakfast2.setVisibility(View.GONE);
            lunch2.setVisibility(View.GONE);
            snaks2.setVisibility(View.GONE);
            dinner2.setVisibility(View.GONE);
        }

        db.close();

    }
}