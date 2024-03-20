package com.vinhbqph33437.assignment.Model;

public class Student {

    private String _id;
    private String name;

    private int age;

    private String mssv;


    public Student(String _id, String name, int tuoi, String mssv) {
        this._id = _id;
        this.name = name;
        this.age = tuoi;
        this.mssv = mssv;
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

    public int getTuoi() {
        return age;
    }

    public void setTuoi(int tuoi) {
        this.age = age;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

}
