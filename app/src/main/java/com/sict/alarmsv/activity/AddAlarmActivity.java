package com.sict.alarmsv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.sict.alarmsv.R;
import com.sict.alarmsv.model.Alarm;
import com.sict.alarmsv.ultil.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
// add thông tin vào adapter
public class AddAlarmActivity extends AppCompatActivity {
    private static final String TAG = "ok";
    @BindView(R.id.toolBarAdd)
    Toolbar toolBarAdd;
    @BindView(R.id.addAlarm)
    Button addAlarm;
    @BindView(R.id.time_Picker)
    TimePicker timePicker;
    @BindView(R.id.activityName)
    TextView activityName;
    @BindView(R.id.name_Alarm)
    EditText name_Alarm;
    @BindView(R.id.name_monhoc)
    EditText name_monhoc;
    @BindView(R.id.name_room)
    EditText name_room;
    @BindView(R.id.name_tiethoc)
    EditText name_tiethoc;
    @BindView(R.id.spinner)
    Spinner selection;
    private boolean addScreen;
    private Alarm alarmEdit;
    private Intent intentInfor;
    public String days = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
                initView();
    }

    // TODO: this initialize view for this  activity
    private void initView() {
        toolBarAdd.setNavigationIcon(R.drawable.ic_back);
        ArrayAdapter<CharSequence> numbers = ArrayAdapter.createFromResource(AddAlarmActivity.this,R.array.selection,android.R.layout.simple_spinner_item);
        numbers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selection.setAdapter(numbers);
        selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                days = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Toast.makeText(this.getApplicationContext(),selection.getSelectedItem().toString(), Toast.LENGTH_SHORT);

        setScreen();
        backPressed();
    }

    // TODO: this process when user press back "<" button
    private void backPressed() {
        toolBarAdd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.RESULT_CANCEL);
                onBackPressed();
            }
        });
    }

    // TODO: lấy giá trị từ activity set vào các edittext
    private void setScreen() {
        intentInfor = getIntent();
        String screenType = intentInfor.getStringExtra("screenType");
        if (screenType.contains("add")) {
            Log.d(TAG, "setScreen: ");
            activityName.setText(R.string.add);
            addAlarm.setText(R.string.add);
            addScreen = true;

        } else if (screenType.contains("edit")) {
            try {
                alarmEdit = (Alarm) intentInfor.getExtras().getSerializable("AlarmEdit");
            } catch (Exception e) {
                Log.e("setScreen exception", e.getMessage() + " cause: " + e.getCause());
            }

            if (alarmEdit != null) {
                timePicker.setHour(alarmEdit.getHour_x());
                timePicker.setMinute(alarmEdit.getMinute_x());
                name_Alarm.setText(alarmEdit.getAlarm_Name());
                name_monhoc.setText(alarmEdit.getMonhoc());
                name_room.setText(alarmEdit.getRoom());
                name_tiethoc.setText(alarmEdit.getTiethoc());
                activityName.setText(R.string.edit);
                addAlarm.setText(R.string.edit);
            }
            addScreen = false;
        }
    }

    @OnClick(R.id.addAlarm)
    public void onClick(View v) {
        // TODO: nhận sử lý edit hoặc add
        Intent intent = new Intent(this, AlarmMainActivity.class);
        // TODO: tạo alarm từ time picker
        Alarm alarm = initAlarm();

        if (addScreen) {
            alarm.setId((int) System.currentTimeMillis());
            intent.putExtra("Alarm", alarm);
            setResult(RESULT_OK, intent);
            finish();

        } else {
            int position = intentInfor.getExtras().getInt("position");

            String name = alarm.getAlarm_Name();
            String name1 = alarm.getMonhoc();
            String name2 = alarm.getRoom();
            String name3 = alarm.getTiethoc();
            int hour = alarm.getHour_x();
            int minute = alarm.getMinute_x();

            alarmEdit.setAlarm_Name(name);
            alarmEdit.setMonhoc(name1);
            alarmEdit.setRoom(name2);
            alarmEdit.setTiethoc(name3);
            alarmEdit.setHour_x(hour);
            alarmEdit.setMinute_x(minute);

            Bundle bundle = new Bundle();
            bundle.putSerializable("Alarm", alarmEdit);
            bundle.putInt("position", position);

            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    // TODO:  this return alarm from timePicker set toggle on by default
    private Alarm initAlarm() {
        int toggleOn = 1;
        Alarm alarm;
        String name1 = null;
        String name2 = null;
        String name3 = null;
        String name4 = null;
        String name5 = "https://tiengnhat.minder.vn/wp-content/uploads/2017/10/icon-accounting.png";
        String thu = null;
        int hour_x = 0;
        int minute_x = 0;
        try {
            hour_x = timePicker.getCurrentHour();
            minute_x = timePicker.getCurrentMinute();
            String name = name_Alarm.getText().toString();

            if (name.length() == 0) {
                name1 = name_Alarm.getHint().toString();
                name2 = name_monhoc.getHint().toString();
                name3 = name_room.getHint().toString();
                name4 = name_tiethoc.getHint().toString();
            } else {
                name1 = name_Alarm.getText().toString();
                name2 = name_monhoc.getText().toString();
                name3 = name_room.getText().toString();
                name4 = name_tiethoc.getText().toString();
                Log.d(TAG, "initAlarm: "+days);
                thu = days;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        alarm = new Alarm(hour_x, minute_x, name1, name2, name3, name4, name5, thu, toggleOn);

        return alarm;
    }
}
