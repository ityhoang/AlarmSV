package com.sict.alarmsv.service;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;



import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.android.volley.VolleyError;
import com.sict.alarmsv.R;
import com.sict.alarmsv.activity.AlarmMainActivity;
import com.sict.alarmsv.database.Getdata;
import com.sict.alarmsv.database.Result;
import com.sict.alarmsv.model.Alarm;
import com.sict.alarmsv.receiver.AlarmReceiver;
import com.sict.alarmsv.ultil.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {
    private static final String CHANNEL_ID = "tyhoang";
    Alarm alarm;
    int hour;
    int minute;
    String monhoc;
    String tiethoc;
    Boolean checktime = false;
    MediaPlayer mediaPlayer;
    private Getdata getDataAlram;
    public Result mResultCallback = null;
    int gh = 0;
    Timer timer = new Timer();
    private NotificationManagerCompat notificationManager;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO: processing on and off ringtone
        // get string from intent
        Log.d("ok ", gh+"");
        String on_Off = intent.getExtras().getString("ON_OFF");
        switch (on_Off) {
            case Constants.ADD_INTENT:
                hour = intent.getExtras().getInt("hour");
                minute = intent.getExtras().getInt("minute");
                minute+=1;
                monhoc = intent.getExtras().getString("monhoc");
                tiethoc = intent.getExtras().getString("tiethoc");
                getDataAlram = new Getdata(mResultCallback,AlarmService.this);
                getDataAlram.PostBai("GETCALL",Constants.linkpost, Constants.cookie, "Môn học: "+monhoc+" - Giờ: " + hour + " : " + ((minute<10)? "0"+minute : minute)+" - Tiết: "+tiethoc);
                initVolleyCallback();
                Log.d("ok ", hour + ":" + minute+" mon học "+monhoc +"tiet hoc "+tiethoc);
                Nofis();
                mediaPlayer = MediaPlayer.create(AlarmService.this,R.raw.nhaccho);
                mediaPlayer.setLooping(true);
//                Uri uri = Settings.System.DEFAULT_ALARM_ALERT_URI;
//                mediaPlayer = MediaPlayer.create(this, uri);
                mediaPlayer.start();
                checktime = true;
                break;
            case Constants.OFF_INTENT:
                int alarmId = intent.getExtras().getInt("AlarmId");
                if (mediaPlayer != null && mediaPlayer.isPlaying() && alarmId == AlarmReceiver.pendingId) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    checktime = false;
                }
                break;
            case Constants.OFF_Nhac:
                Log.d("ok", "onStartCommand: ");
                mediaPlayer.stop();
                mediaPlayer.reset();
                checktime = false;
                break;
            case Constants.START_INTENT:
                Nofi();
                sendMyBroadCast("true");
                break;
            case Constants.STOP_INTENT:
//                stopForeground(true);
                stopSelf();
                sendMyBroadCast("false");
                break;
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (checktime) {
                    if (Calendar.getInstance().getTime().getHours() == hour &&
                            Calendar.getInstance().getTime().getMinutes() == minute) {
                        Log.d("ok ", "run: ");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        checktime=false;
                    }
                }
            }
        }, 0, 2000);

        return START_STICKY;
    }
    public void initVolleyCallback(){
        mResultCallback = new Result() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + response);
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + response);
            }

            @Override
            public void LoadData(String requestType, JSONObject response) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("Danhsach");
                    JSONObject jsonObject = null;
                    String checkpost = null;

                    for (int a = 0; a < jsonArray.length(); a++) {
                        jsonObject = jsonArray.getJSONObject(a);
                        checkpost = (jsonObject.getString("post_supports_client_mutation_id"));
                        Log.d("ok", "LoadData: "+checkpost);
                        if(checkpost.equals("true")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(AlarmService.this);
                            builder.setTitle("Alarm Clock");
                            builder.setIcon(R.drawable.cl2);
                            builder.setMessage("post thành công");
                            builder.setCancelable(true);
                        }else if(checkpost.equals("false")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(AlarmService.this);
                            builder.setTitle("Alarm Clock");
                            builder.setIcon(R.drawable.cl2);
                            builder.setMessage("post lỗi");
                            builder.setCancelable(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + "That didn't work!");
            }
        };
    }
    public void Nofi() {
        try {
            Intent notificationIntent = new Intent(this.getApplicationContext(), AlarmMainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(this.getApplicationContext(), CHANNEL_ID)
                    .setContentTitle("AlarmClock")
                    .setContentText("Setup time")
                    .setSmallIcon(R.drawable.lock)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Nofis() {
        try {
            Intent s = new Intent(this.getApplicationContext(), AlarmService.class);
            PendingIntent sf = PendingIntent.getActivity(this.getApplicationContext(), 0, s, 0);
            Intent notificationIntent = new Intent(this.getApplicationContext(), AlarmMainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(this.getApplicationContext(), CHANNEL_ID)
                    .setContentTitle("AlarmLock")
                    .setContentText(monhoc+" - " + hour + " : " + ((minute<10)? "0"+minute : minute)+" - "+tiethoc)
                    .setSmallIcon(R.drawable.lock)
                    .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.ic_cancel_24,"Stop", sf)
                    .build();
//
            startForeground(1, notification);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendMyBroadCast(String send) {
        try {
            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(AlarmMainActivity.ACTIVITY_SERVICE);
            broadCastIntent.putExtra("data", send);
            sendBroadcast(broadCastIntent);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //TODO: Xử lý logic tắt nhạc chuông
        try {
//            Log.d("ok ", mediaPlayer.isPlaying()+"");
//            mediaPlayer.stop();
            mediaPlayer.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
