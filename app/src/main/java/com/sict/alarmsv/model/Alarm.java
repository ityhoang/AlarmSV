package com.sict.alarmsv.model;

import java.io.Serializable;

public class Alarm implements Serializable {

    private int id;
    private int hour_x;
    private int minute_x;
    private String alarm_Name;
    private String monhoc;
    private String room;
    private String tiethoc;
    private String img;
    private String thu;
    private int onOff;

    public Alarm(int id, int hour_x, int minute_x, String alarm_Name, String monhoc, String room, String tiethoc, String img, int onOff) {
        this.id = id;
        this.hour_x = hour_x;
        this.minute_x = minute_x;
        this.alarm_Name = alarm_Name;
        this.monhoc = monhoc;
        this.room = room;
        this.tiethoc = tiethoc;
        this.img = img;
        this.thu = thu;
        this.onOff = onOff;
    }

    public Alarm(int hour_x, int minute_x, String alarm_Name, String monhoc, String room, String tiethoc, String img, String thu, int onOff) {
        this.hour_x = hour_x;
        this.minute_x = minute_x;
        this.alarm_Name = alarm_Name;
        this.monhoc = monhoc;
        this.room = room;
        this.tiethoc = tiethoc;
        this.img = img;
        this.thu = thu;
        this.onOff = onOff;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour_x() {
        return hour_x;
    }

    public void setHour_x(int hour_x) {
        this.hour_x = hour_x;
    }

    public int getMinute_x() {
        return minute_x;
    }

    public void setMinute_x(int minute_x) {
        this.minute_x = minute_x;
    }

    public String getAlarm_Name() {
        return alarm_Name;
    }

    public void setAlarm_Name(String alarm_Name) {
        this.alarm_Name = alarm_Name;
    }

    public String getMonhoc() {
        return monhoc;
    }

    public void setMonhoc(String monhoc) {
        this.monhoc = monhoc;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTiethoc() {
        return tiethoc;
    }

    public void setTiethoc(String tiethoc) {
        this.tiethoc = tiethoc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
    }
}