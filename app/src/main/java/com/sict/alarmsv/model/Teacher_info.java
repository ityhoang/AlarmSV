package com.sict.alarmsv.model;

public class Teacher_info {
    private String name;
    private String cv;
    private String img_teacher;
    private String nganh;
    private String linh_vuc;

    public Teacher_info(String name, String cv, String img_teacher, String nganh, String linh_vuc) {
        this.name = name;
        this.cv = cv;
        this.img_teacher = img_teacher;
        this.nganh = nganh;
        this.linh_vuc = linh_vuc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getImg_teacher() {
        return img_teacher;
    }

    public void setImg_teacher(String img_teacher) {
        this.img_teacher = img_teacher;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String getLinh_vuc() {
        return linh_vuc;
    }

    public void setLinh_vuc(String linh_vuc) {
        this.linh_vuc = linh_vuc;
    }
}