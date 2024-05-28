package com.example.tasker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel", "Channel!", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("The Channel is Very Important");

            NotificationManagerCompat manager=getSystemService(NotificationManagerCompat.class);
            manager.createNotificationChannel(channel);

        }
    }

}
