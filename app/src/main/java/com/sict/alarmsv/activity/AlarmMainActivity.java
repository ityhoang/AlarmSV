package com.sict.alarmsv.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.navigation.NavigationView;
import com.sict.alarmsv.R;
import com.sict.alarmsv.database.Getdata;
import com.sict.alarmsv.database.Result;
import com.sict.alarmsv.model.Alarm;
import com.sict.alarmsv.model.Session;
import com.sict.alarmsv.receiver.AlarmReceiver;
import com.sict.alarmsv.ultil.Constants;
import com.sict.alarmsv.view.AlarmAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmMainActivity extends AppCompatActivity implements AlarmAdapter.CallBack {

    @BindView(R.id.calendarView)
    CalendarView calendar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.openAdd)
    Button button;
    @BindView(R.id.buttonClose)
    Button btn;
    @BindView(R.id.divider4)
    View divider;
    @BindView(R.id.alarmView)
    RecyclerView recyclerView;
    private AlarmAdapter alarmAdapter;
    MyBroadCastReceiver myBroadCastReceiver;
    String checker = "false";
    private Getdata getDataAlram;
    public Result mResultCallback = null;
    public ArrayList<Alarm> Arrdata;
    public Session session;
    public HashMap<String,String> user ;
    public String usertest;
    public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        session = new Session(this);
        session.checkLogin();
        user = session.getUserDetail();
        usertest = user.get(session.NAME);
        name = usertest;

        name = name.substring(0,name.indexOf('@'));
        name = name.substring(name.indexOf('.')+1,name.length());
        setUpAlram();
        initVolleyCallback();
        getDataAlram = new Getdata(mResultCallback,this);
        getDataAlram.getDataVolley("GETCALL",Constants.Data,name);
    }
    public void setUpAlram(){
        Arrdata = new ArrayList<Alarm>();
        alarmAdapter = new AlarmAdapter(Arrdata, this);
        recyclerView.setAdapter(alarmAdapter);
        myBroadCastReceiver = new MyBroadCastReceiver();
    }
    @OnClick(R.id.buttonClose)
    public void onCloseCalendar(View view){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calendar.isShown()){
                    btn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    divider.setVisibility(divider.INVISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(0, 10000, 0, 0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    calendar.startAnimation(animate);
                    calendar.setVisibility(View.GONE);
                }
                else {
                    calendar.setVisibility(View.VISIBLE);
                    divider.setVisibility(divider.VISIBLE);
                    btn.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    TranslateAnimation animate = new TranslateAnimation(-10000, 0, 0, 0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    calendar.startAnimation(animate);
                    TranslateAnimation animate1 = new TranslateAnimation(0, 0, -800, 0);
                    animate1.setDuration(500);
                    animate1.setFillAfter(true);
                    recyclerView.startAnimation(animate1);
                }
            }
        });
    }
    private void setAlarmAPI(Alarm alarm, int flags) {
        Calendar calender= Calendar.getInstance();
        calender.set(Calendar.DAY_OF_WEEK, Checkdays(alarm.getThu()));
        calender.set(Calendar.HOUR_OF_DAY, alarm.getHour_x());
        calender.set(Calendar.MINUTE, alarm.getMinute_x());
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);
        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        if (calender.before(now)) {
            calender.add(Calendar.DATE, 7);
        }
        int alarmId = (int) alarm.getId();
        Log.d("ok", "setAlarmAPI: "+alarmId);
        Intent intent = new Intent(AlarmMainActivity.this, AlarmReceiver.class);
        intent.putExtra("intentType", Constants.ADD_INTENT);
        intent.putExtra("hour", alarm.getHour_x());
        intent.putExtra("minute", alarm.getMinute_x());
        intent.putExtra("monhoc", alarm.getMonhoc());
        intent.putExtra("tiethoc", alarm.getTiethoc());
        intent.putExtra("PendingId", alarmId);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(AlarmMainActivity.this, alarmId,
                intent, flags);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calender.getTimeInMillis(), 7 * AlarmManager.INTERVAL_DAY, alarmIntent);
    }
    public void initVolleyCallback(){
        mResultCallback = new Result() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + response);
                try {
                    JSONArray jsonArray = response.getJSONArray("DanhSach");
                    JSONObject jsonObject = null;
                    String hour = null;
                    String minute = null;
                    String giaovien = null;
                    String monhoc = null;
                    String phong = null;
                    String tiet = null;
                    String img = null;
                    String thu = null;
                    String chedo = null;
                    String id = null;
                    for (int a = 0; a < jsonArray.length(); a++) {
                        jsonObject = jsonArray.getJSONObject(a);
                        hour = (jsonObject.getString("hour"));
                        minute = (jsonObject.getString("minute"));
                        giaovien = (jsonObject.getString("teacher"));
                        monhoc = (jsonObject.getString("subjects"));
                        phong = (jsonObject.getString("classroom"));
                        tiet = (jsonObject.getString("lesson"));
                        img = (jsonObject.getString("img"));
                        thu = (jsonObject.getString("days"));
                        chedo = (jsonObject.getString("chedo"));
                        id = (jsonObject.getString("id"));
                        Alarm alarm = new Alarm(Integer.parseInt(hour), Integer.parseInt(minute), giaovien, monhoc, phong, tiet, img, thu, Integer.parseInt(chedo));
                        alarm.setId(Integer.parseInt(id));
                        Arrdata.add(alarm);
                        alarmAdapter.notifyDataSetChanged();
                        if(chedo.equals("1"))
                        setAlarmAPI(alarm, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + "That didn't work!");
            }
        };
        registerMyReceiver();
        if (checker.equals("false"))
            sendIntentService(Constants.START_INTENT);
        initView();
    }

    // TODO: set dữ liệu đầu vào
    private void initView() {
        Calender();
        Navigation();
        importData();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(alarmAdapter);

    }
    // TODO:calender
    protected void Calender() {
//        View header = LayoutInflater.from(this).inflate(R.layout.alarm_item, null);
//        calendar.addView(header,);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(
                    CalendarView view,
                    int year,
                    int month,
                    int dayOfMonth) {
            }
        });
    }
    // TODO:navigation;
    protected void Navigation() {
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        View header = LayoutInflater.from(this).inflate(R.layout.navi_header, null);
        navigationView.addHeaderView(header);
        TextView text = (TextView) header.findViewById(R.id.emails);

        text.setText(usertest);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timetbl:
                Intent intent1 = new Intent(AlarmMainActivity.this, Timetable.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.teacher:
                Intent intent = new Intent(AlarmMainActivity.this, Teacher.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logout:
                sendIntentService(Constants.STOP_INTENT);
                session.logout();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //TODO: onclick "+"
    @OnClick(R.id.openAdd)
    public void onOpenAddAlarm(View view) {
        Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
        intent.putExtra("screenType", "add");
        startActivityForResult(intent, Constants.REQUEST_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO: sử lý thêm hoặc sửa và truyền qua receiver
        Alarm alarm;
        if (requestCode == Constants.REQUEST_ADD && resultCode == RESULT_OK) {
            alarm = (Alarm) data.getSerializableExtra("Alarm");
            boolean containAlarm = checkAlarm(alarm);

            if (!containAlarm) {
                alarmAdapter.add(alarm);
                alarmAdapter.notifyDataSetChanged();
                getDataAlram.insert("ADD",Constants.Insert,alarm, name);
                setAlarm(alarm, 0);
            }
        } else if (requestCode == Constants.REQUEST_EDIT && resultCode == RESULT_OK) {
            alarm = (Alarm) data.getSerializableExtra("Alarm");
            boolean containAlarm = checkAlarm(alarm);
            if (!containAlarm) {
                int position = data.getExtras().getInt("position");
                alarmAdapter.updateAlarm(alarm, position);
                alarmAdapter.notifyDataSetChanged();
                getDataAlram.update("Update",Constants.Update,alarm);
                if (alarm.getOnOff() == 1) {
                    setAlarm(alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                }
            }
        }
    }

    @Override
    public void onMenuAction(Alarm alarm, MenuItem item, int position) {
        // TODO: truyền dữ liệu qua AlarmAdapter
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(this, AddAlarmActivity.class);
                intent.putExtra("screenType", "edit");
                intent.putExtra("AlarmEdit", alarm);
                intent.putExtra("position", position);
                startActivityForResult(intent, Constants.REQUEST_EDIT);
                break;

            case R.id.delete:
//                alarmAdapter.removeAlarm(position);
//                alarmAdapter.notifyDataSetChanged();
                int alarmId = (int) alarm.getId();
                showAlertDialog(alarm,alarmId,position);
//                getDataAlram.delete("Delete",Constants.Delete,alarmId);
//                deleteCancel(alarm);
                break;
        }
    }

    @Override
    public void startAlarm(Alarm alarm) {
        //TODO: Xử lý truyền thông tin giờ hẹn cho AlarmReceiver
        alarm.setOnOff(1);
        getDataAlram.update("updata",Constants.Update,alarm);
        setAlarm(alarm, 0);
    }

    @Override
    public void cancelAlarm(Alarm timeItem) {
        timeItem.setOnOff(0);
        getDataAlram.update("Updata",Constants.Update,timeItem);
        deleteCancel(timeItem);
        sendIntent(timeItem, Constants.OFF_INTENT);
        ArrayList<Alarm> test = Arrdata;
        boolean chck = true;
        for (int i = 0; i < test.size(); i++) {
            if (test.get(i).getOnOff() == 1) {
                chck = false;
                break;
            }
        }
        if (chck) {
            sendIntentService(Constants.STOP_INTENT);
        } else {
            sendIntent(timeItem, Constants.OFF_INTENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(myBroadCastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkAlarm(Alarm alarm) {
        boolean contain = false;
        for (Alarm alar : alarmAdapter.getmAlarms()) {
            if (alar.getHour_x() == alarm.getHour_x() && alar.getMinute_x() == alarm.getMinute_x()){
                switch (alar.getThu()){
                    case "1" :
                    case "2" :
                    case "3" :
                    case "4" :
                    case "5" :
                    case "6" :
                    case "7" : contain = true; break;
                }
            }

        }
        if (contain) {
            Toast.makeText(this, "Giờ Đặt Của Bạn Bị Trùng", Toast.LENGTH_SHORT).show();
        }
        return contain;
    }

    // TODO: thêm dữ liệu vào Adapter
    private void importData() {
        if (alarmAdapter == null) {
            ArrayList<Alarm> arrayList = Arrdata;
            alarmAdapter = new AlarmAdapter(arrayList, this);
        }
    }

    // TODO: gửi đến AlarmReceiver
    private void sendIntent(Alarm alarm, String intentType) {
        Intent intent1 = new Intent(AlarmMainActivity.this, AlarmReceiver.class);
        intent1.putExtra("intentType", intentType);
        intent1.putExtra("AlarmId", (int) alarm.getId());
        sendBroadcast(intent1);
    }

    private void sendIntentService(String intentType) {
        Intent intent1 = new Intent(AlarmMainActivity.this, AlarmReceiver.class);
        intent1.putExtra("intentType", intentType);
        sendBroadcast(intent1);
    }

    public int Checkdays(String days){
        if(days.equals("MONDAY")){
            return 2;
        }else if(days.equals("TUESDAY")){
            return 3;
        }else if(days.equals("WEDNESDAY")){
            return 4;
        }else if(days.equals("THURSDAY")){
            return 5;
        }else if(days.equals("FRIDAY")){
            return 6;
        }else if(days.equals("SATURDAY")){
            return 7;
        }else if(days.equals("SUNDAY")){
            return 1;
        }
        return 0;
    }
    // TODO: chờ xử lý gửi dữ liệu đi
    private void setAlarm(Alarm alarm, int flags) {
        registerMyReceiver();
        if (checker.equals("false"))
            sendIntentService(Constants.START_INTENT);
        Calendar calender= Calendar.getInstance();
        Log.d("ok", "setAlarm: "+alarm.getThu());
//        int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        calender.set(Calendar.DAY_OF_WEEK, Checkdays(alarm.getThu()));
        calender.set(Calendar.HOUR_OF_DAY, alarm.getHour_x());
        calender.set(Calendar.MINUTE, alarm.getMinute_x());
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);
        Log.d("ok", "test: "+alarm.getId());
        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        if (calender.before(now)) {
            calender.add(Calendar.DATE, 7);
        }
        int alarmId = (int) alarm.getId();
        Intent intent = new Intent(AlarmMainActivity.this, AlarmReceiver.class);
        intent.putExtra("intentType", Constants.ADD_INTENT);
        intent.putExtra("hour", alarm.getHour_x());
        intent.putExtra("minute", alarm.getMinute_x());
        intent.putExtra("monhoc", alarm.getMonhoc());
        intent.putExtra("tiethoc", alarm.getTiethoc());
        intent.putExtra("PendingId", alarmId);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(AlarmMainActivity.this, alarmId,
                intent, flags);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calender.getTimeInMillis(), 7 * AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    // TODO:  hủy pendingIntent của alarm
    private void deleteCancel(Alarm alarm) {
        ArrayList<Alarm> test = Arrdata;
        boolean chck = true;
        for (int i = 0; i < test.size(); i++) {
            if (test.get(i).getOnOff() == 1) {
                chck = false;
                break;
            }
        }
        if (chck) {
            sendIntentService(Constants.STOP_INTENT);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int alarmId = (int) alarm.getId();
        Intent intent = new Intent(AlarmMainActivity.this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(AlarmMainActivity.this, alarmId, intent, 0);
        alarmManager.cancel(alarmIntent);
    }

    private void registerMyReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTIVITY_SERVICE);
            registerReceiver(myBroadCastReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String data = intent.getStringExtra("data");
                checker = data;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void showAlertDialog(Alarm alarm, int alarmId,int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alarm Clock");
        builder.setIcon(R.drawable.cl2);
        builder.setMessage("Bạn có muốn xóa lịch học này đi không ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Không xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteCancel(alarm);
                getDataAlram.delete("Delete",Constants.Delete,alarmId);
                alarmAdapter.removeAlarm(position);
                alarmAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

