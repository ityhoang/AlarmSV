package com.sict.alarmsv.ultil;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "tyhoang";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel sev = new NotificationChannel(
                    CHANNEL_ID,
                    "tyhoang",
                    NotificationManager.IMPORTANCE_HIGH
            );
            sev.setDescription("This is Channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(sev);
        }
    }
}
