package com.sict.alarmsv.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sict.alarmsv.R;
import com.sict.alarmsv.model.Teacher_info;
import com.sict.alarmsv.view.TeacherAdapter;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends AppCompatActivity {
    RecyclerView rvItems;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        btn= findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher.this,AlarmMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        List<Teacher_info> item = new ArrayList<>();
        item.add(new Teacher_info("Huỳnh Công Pháp","(Trưởng Khoa)","http://sict.udn.vn/uploads/cocau/hcphap.png","Công nghệ Thông tin","Mạng máy tính, Lập trình mạng, Web ngữ nghĩa"));
        item.add(new Teacher_info("Nguyễn Thanh Bình","","http://sict.udn.vn/uploads/cocau/ntbinh.png","Công nghệ Thông tin","Công nghệ phần mềm, kiểm thử phần mềm"));
        item.add(new Teacher_info("Nguyễn Lê Hùng","","http://sict.udn.vn/uploads/cocau/nlhung.jpg","Công nghệ Thông tin","Điện tử, hệ thống nhúng , xử lý ảnh"));
        item.add(new Teacher_info("Võ Trung Hùng","","http://sict.udn.vn/uploads/cocau/vthung.jpg","Công nghệ Thông tin","Công nghệ phần mềm, quản lý dự án phần mềm"));
        item.add(new Teacher_info("ThS.Võ Hùng Cường","","http://sict.udn.vn/uploads/cocau/vhcuong.png","Công nghệ Thông tin","Khởi nghiệp, quản trị thương mại điện tử"));
        item.add(new Teacher_info("Phan Thị Thu Huyền","","http://sict.udn.vn/uploads/cocau/ptthuyen.png","Tiếng Anh","Ngành: Quản trị kinh doanh, khởi nghiệp"));
        rvItems = (RecyclerView) findViewById(R.id.rcv_teacher);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setAdapter(new TeacherAdapter(this, item));
    }
}