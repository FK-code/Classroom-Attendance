package com.example.classroomattendance;


import android.widget.TextView;

public class Course {
    private String coursename;

    public Course() {

    }

    public Course(String coursename) {
        this.coursename = coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoursename() {
        return coursename;
    }
}
