package com.sict.alarmsv.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.sict.alarmsv.R;
import com.sict.alarmsv.database.Getdata;
import com.sict.alarmsv.database.Result;
import com.sict.alarmsv.model.Alarm;
import com.sict.alarmsv.model.Session;
import com.sict.alarmsv.model.Timetable_info;
import com.sict.alarmsv.ultil.Constants;
import com.sict.alarmsv.view.AlarmAdapter;
import com.sict.alarmsv.view.TimetableAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Timetable extends AppCompatActivity {
    @BindView(R.id.rcv_timetable)
    RecyclerView rvItems;
    Button btn;
    private Getdata getDataAlram;
    public Result mResultCallback = null;
    List<Timetable_info> item;
    public ArrayList<Alarm> Arrdata;
    public TimetableAdapter timetableAdapter;
    public Session session;
    public HashMap<String, String> user;
    public String usertest;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.bind(this);
        btn = findViewById(R.id.btn_table_Back);
        setUpAlram();
        initVolleyCallback();

        session = new Session(this);
        session.checkLogin();
        user = session.getUserDetail();
        usertest = user.get(session.NAME);
        name = usertest;

        name = name.substring(0, name.indexOf('@'));
        name = name.substring(name.indexOf('.')+1, name.length());
        getDataAlram = new Getdata(mResultCallback, this);
        getDataAlram.getDataVolley("GETCALL", Constants.Data, name);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Timetable.this, AlarmMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initVolleyCallback() {
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
                        timetableAdapter.notifyDataSetChanged();
                    }
                    initview();
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

    }

    public void initview() {
        Timetable_info test = importData("MONDAY");
        test.setDate("Thứ 2");
        if (checker(test.getT1(), test.getT2(), test.getT3(), test.getT4()))
            item.add(test);

        test = importData("TUESDAY");
        test.setDate("Thứ 3");
        if (checker(test.getT1(), test.getT2(), test.getT3(), test.getT4()))
            item.add(test);

        test = importData("WEDNESDAY");
        test.setDate("Thứ 4");
        if (checker(test.getT1(), test.getT2(), test.getT3(), test.getT4()))
            item.add(test);

        test = importData("THURSDAY");
        test.setDate("Thứ 5");
        if (checker(test.getT1(), test.getT2(), test.getT3(), test.getT4()))
            item.add(test);

        test = importData("FRIDAY");
        test.setDate("Thứ 6");
        if (checker(test.getT1(), test.getT2(), test.getT3(), test.getT4()))
            item.add(test);

        test = importData("SATURDAY");
        test.setDate("Thứ 7");
        if (checker(test.getT1(), test.getT2(), test.getT3(), test.getT4()))
            item.add(test);

        test = importData("SUNDAY");
        test.setDate("Chủ Nhật");
        if (checker(test.getT1(), test.getT2(), test.getT3(), test.getT4()))
            item.add(test);


        rvItems.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(timetableAdapter);
    }

    public boolean checker(String t, String t1, String t2, String t3) {
        if (t.equals(" ") && t1.equals(" ") && t2.equals(" ") && t3.equals(" ")) {
            return false;
        }
        return true;
    }

    private Timetable_info importData(String thu) {
        String mon1 = " ";
        String mon2 = " ";
        String mon3 = " ";
        String mon4 = " ";
        for (int i = 0; i < Arrdata.size(); i++) {
            if (Arrdata.get(i).getThu().equals(thu)) {
                if (Arrdata.get(i).getTiethoc().equals("1->2")) {
                    mon1 = Arrdata.get(i).getMonhoc();
                } else if (Arrdata.get(i).getTiethoc().equals("3->4") || Arrdata.get(i).getTiethoc().equals("3->5")) {
                    mon2 = Arrdata.get(i).getMonhoc();
                } else if (Arrdata.get(i).getTiethoc().equals("1->4")) {
                    mon1 = Arrdata.get(i).getMonhoc();
                    mon2 = Arrdata.get(i).getMonhoc();
                } else if (Arrdata.get(i).getTiethoc().equals("6->7")) {
                    mon3 = Arrdata.get(i).getMonhoc();
                } else if (Arrdata.get(i).getTiethoc().equals("8->9") || Arrdata.get(i).getTiethoc().equals("8->10")) {
                    mon4 = Arrdata.get(i).getMonhoc();
                } else if (Arrdata.get(i).getTiethoc().equals("6->9")) {
                    mon3 = Arrdata.get(i).getMonhoc();
                    mon4 = Arrdata.get(i).getMonhoc();
                }
            }
        }
        Timetable_info lich = new Timetable_info(thu, mon1, mon2, mon3, mon4);
        return lich;
    }

    public void setUpAlram() {
        Arrdata = new ArrayList<Alarm>();
        item = new ArrayList<Timetable_info>();
        timetableAdapter = new TimetableAdapter(this, item);
        rvItems.setAdapter(timetableAdapter);
    }
}