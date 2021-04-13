package com.example.classroomattendance;

public class StudentHelper {
    String stid,name;

    public StudentHelper() {
    }

    public StudentHelper(String stid, String name) {
        this.stid = stid;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }
}
