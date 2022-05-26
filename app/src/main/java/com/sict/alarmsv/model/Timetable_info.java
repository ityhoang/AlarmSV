package com.sict.alarmsv.model;

public class Timetable_info {
    private String date;
    private String t1;
    private String t2;
    private String t3;
    private String t4;
        public Timetable_info(String date, String t1, String t2, String t3,String t4){
            this.date=date;
            this.t1=t1;
            this.t2=t2;
            this.t3=t3;
            this.t4=t4;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public String getT4() {
        return t4;
    }

    public void setT4(String t4) {
        this.t4 = t4;
    }
}
