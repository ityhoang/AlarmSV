package com.sict.alarmsv.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sict.alarmsv.R;
import com.sict.alarmsv.activity.AlarmMainActivity;
import com.sict.alarmsv.service.AlarmService;
import com.sict.alarmsv.ultil.Constants;

public class AlarmReceiver extends BroadcastReceiver {
    public static int pendingId;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        if (intent != null) {
            Intent intentToService = new Intent(context, AlarmService.class);
            try {
                String intentType = intent.getExtras().getString("intentType");
                switch (intentType) {
                    case Constants.OFF_Nhac:
                        intentToService.putExtra("ON_OFF", Constants.OFF_Nhac);
                        context.startService(intentToService);
                        break;
                    case Constants.ADD_INTENT:
                        int hour = intent.getExtras().getInt("hour");
                        int minute = intent.getExtras().getInt("minute");
                        String monhoc = intent.getExtras().getString("monhoc");
                        String tiethoc = intent.getExtras().getString("tiethoc");
                        // assign pendingId
                        pendingId = intent.getExtras().getInt("PendingId");

                        intentToService.putExtra("ON_OFF", Constants.ADD_INTENT);
                        intentToService.putExtra("hour", hour);
                        intentToService.putExtra("minute", minute);
                        intentToService.putExtra("monhoc", monhoc);
                        intentToService.putExtra("tiethoc", tiethoc);
                        context.startService(intentToService);

                        break;
                    case Constants.OFF_INTENT:
                        int alarmId = intent.getExtras().getInt("AlarmId");
                        intentToService.putExtra("ON_OFF", Constants.OFF_INTENT);
                        intentToService.putExtra("AlarmId", alarmId);
                        context.startService(intentToService);

                        break;
                    case Constants.START_INTENT:
                        intentToService.putExtra("ON_OFF", Constants.START_INTENT);
                        context.startService(intentToService);

                        break;
                    case Constants.STOP_INTENT:
                        intentToService.putExtra("ON_OFF", Constants.STOP_INTENT);
                        context.startService(intentToService);

                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
