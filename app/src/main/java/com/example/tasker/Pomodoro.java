package com.example.tasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Pomodoro extends AppCompatActivity {


    int seconds = 0;
    int value = 0;
    public static final String number = "Value";
    public static final String myPref = "pref";
    public static final String mintAchive = "mints";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        TextView timer;
        CountDownTimer countDownTimer;
        timer = findViewById(R.id.timer);

        Button start, end, home, reset;
        start = findViewById(R.id.start);
        end = findViewById(R.id.End);
        home = findViewById(R.id.home);
        Intent intent = getIntent();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        value = intent.getIntExtra(number, -1);
        if (value != -1) {
            timer.setText(value + " : 00");
            seconds = value * 60 * 1000;
        }

        countDownTimer = new CountDownTimer(seconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 % 60 <= 9 && millisUntilFinished / 1000 / 60 <= 9) // seconds <10 && mints<10
                    timer.setText("0" + String.valueOf(millisUntilFinished / 1000 / 60) + " : 0" + String.valueOf(millisUntilFinished / 1000 % 60));

                else if (millisUntilFinished / 1000 / 60 <= 9)// if mints<10
                {
                    if (millisUntilFinished / 1000 % 60 <= 9)
                        timer.setText("0" + String.valueOf(millisUntilFinished / 1000 / 60) + " : 0" + String.valueOf(millisUntilFinished / 1000 % 60));
                    else
                        timer.setText("0" + String.valueOf(millisUntilFinished / 1000 / 60) + " : " + String.valueOf(millisUntilFinished / 1000 % 60));
                } else if (millisUntilFinished / 1000 / 60 >= 10) {
                    if (millisUntilFinished / 1000 % 60 <= 9)
                        timer.setText(String.valueOf(millisUntilFinished / 1000 / 60) + " : 0" + String.valueOf(millisUntilFinished / 1000 % 60));
                    else
                        timer.setText(String.valueOf(millisUntilFinished / 1000 / 60) + " : " + String.valueOf(millisUntilFinished / 1000 % 60));
                }
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onFinish() {
                timer.setText("Finished");
                SharedPreferences preferences = getSharedPreferences(myPref, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                int lastValue = preferences.getInt(mintAchive, -1);
                if (lastValue != -1) ;
                {
                    lastValue += value;
                }
                editor.putInt(mintAchive, lastValue);
                editor.commit();
                end.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);



                NotificationCompat.Builder builder = new NotificationCompat.Builder(Pomodoro.this, "channel")
                        .setContentTitle("Pomodoro")
                        .setContentText("You Finished Your Task")
                        .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH);

                NotificationManagerCompat manager = NotificationManagerCompat.from(Pomodoro.this);
            }
        };

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
                start.setVisibility(View.GONE);
                end.setVisibility(View.VISIBLE);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pomodoro.this, Dashboard.class));
                finish();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Pomodoro.this)
                        .setMessage("Exit From Task")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                countDownTimer.cancel();
                                timer.setText(value + " : 00");
                                end.setVisibility(View.GONE);
                                start.setVisibility(View.VISIBLE);
                                home.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }
        });

        Button btnTodo = findViewById(R.id.btnTodo);

        // Set onClick Listener
        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Pomodoro activity
                Intent intent = new Intent(Pomodoro.this, todo.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Pomodoro.this, Dashboard.class));
        finish();
    }
}