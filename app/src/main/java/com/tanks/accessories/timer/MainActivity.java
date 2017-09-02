package com.tanks.accessories.timer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnTimer;
    private TextView tvTimer;
    private boolean isOn;       //flag to know whether timer is running or not
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTimer = (Button) findViewById(R.id.startTimer);
        tvTimer = (TextView) findViewById(R.id.tvTimer);

        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOn) {
                    StopTimer();
                }else {
                    StartTimer(5);      //start timer from 5 seconds
                }
            }
        });
    }


    public void StartTimer(int startTime) {
        timeInMilliseconds = startTime*1000;        //convert secs to milliseconds
        customHandler.postDelayed(updateTimerThread, 0);
        isOn=true;
        btnTimer.setText(R.string.stop_timer);
        btnTimer.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
    }

    public void StopTimer() {
        customHandler.removeCallbacks(updateTimerThread);
        isOn = false;
        btnTimer.setText(R.string.reset_timer);
        btnTimer.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds= timeInMilliseconds+1000;
            Log.d("timeMilliseconds:", String.valueOf(timeInMilliseconds));

            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int hours = mins / 60;
            mins = mins % 60;
            //int milliseconds = (int) (updatedTime % 1000);
            //+ ":" + String.format("%03d", milliseconds)
            String timer = "" + String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);

            tvTimer.setText(timer);     //setting up the textView with timer

            customHandler.postDelayed(this, 1000);
        }

    };

}
