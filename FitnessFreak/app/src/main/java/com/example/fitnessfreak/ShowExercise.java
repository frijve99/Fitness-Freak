package com.example.fitnessfreak;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class ShowExercise extends AppCompatActivity {

    private Button startButton;

    private TextView eName;
    private Button stopButton;
    private TextView timerTextView;
    private int btn=0;
    GifImageView gifImageView;

    private AsyncTask<Long, Long, Void> timerTask;
    private long timeLeftInMillis = 60000; // one minute in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercise);

        Intent I = this.getIntent();
        String Exercise_key = I.getStringExtra("Exercise_key");

        KeyValueDB db = new KeyValueDB(ShowExercise.this);
        String value = db.getValueByKey(Exercise_key);
        String values[] = value.split("---");


        startButton = findViewById(R.id.startbutton);
        stopButton = findViewById(R.id.stopbutton);
        timerTextView = findViewById(R.id.time);
        eName = findViewById(R.id.Wname);
        gifImageView = findViewById(R.id.gif);



        String g = "android.resource://" + "com.example.fitnessfreak" + "/drawable/" + values[3];
        Uri uri = Uri.parse(g);
        gifImageView.setImageURI(uri);

        eName.setText(values[0]);
        btnshow();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn=1;
                btnshow();
                timeLeftInMillis = 60000;
                updateTimer();
                startTimer();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn=0;
                btnshow();
                stopTimer();
                timeLeftInMillis = 60000;
                updateTimer();
            }
        });
    }

    private void btnshow(){
        if(btn==1){
            startButton.setVisibility(View.INVISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        }
        else{
            stopButton.setVisibility(View.INVISIBLE);
            startButton.setVisibility(View.VISIBLE);
        }
    }
    private void startTimer() {
        if (timerTask != null && timerTask.getStatus() == AsyncTask.Status.RUNNING) {
            timerTask.cancel(true); // cancel the existing timer task if still running
        }

        timerTask = new AsyncTask<Long, Long, Void>() {
            @Override
            protected Void doInBackground(Long... params) {
                long millisUntilFinished = params[0];
                while (millisUntilFinished > 0 && !isCancelled()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // Handle the InterruptedException gracefully
                        return null;
                    }
                    millisUntilFinished -= 1000;
                    publishProgress(millisUntilFinished);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Long... values) {
                timeLeftInMillis = values[0];
                System.out.println(timeLeftInMillis);
                updateTimer();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                timeLeftInMillis = 0;
                timerTextView.setText("Task Completed");

                NotificationCompat.Builder builder = new NotificationCompat.Builder(ShowExercise.this, "default")
                        .setSmallIcon(R.drawable.lunch)
                        .setContentTitle("Time Completed")
                        .setContentText("Your have completed your task!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ShowExercise.this);
                if (ActivityCompat.checkSelfPermission(ShowExercise.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                btn=0;
                btnshow();
                notificationManager.notify(0, builder.build());
            }
        };
        timerTask.execute(timeLeftInMillis);
    }

    private void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel(true);
            System.out.println(timerTask);// cancel the running timer task
        }
    }

    private void updateTimer() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        timerTextView.setText(timeLeftFormatted);
    }
}
