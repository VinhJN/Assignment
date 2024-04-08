package com.vinhbqph33437.assignment.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {

    private String _id;
    private String name;
    private String mssv;

    private String point;

    private ArrayList<String> avatar;
    private String createdAt, updatedAt;

    public Student() {
    }

    public Student(String _id, String name, String mssv, String point, ArrayList<String> avatar, String createdAt, String updatedAt) {
        this._id = _id;
        this.name = name;
        this.mssv = mssv;
        this.point = point;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public ArrayList<String> getAvatar() {
        return avatar;
    }

    public void setAvatar(ArrayList<String> avatar) {
        this.avatar = avatar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
