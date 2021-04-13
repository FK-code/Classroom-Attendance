package com.example.classroomattendance;

public class TeacherHelper {
    private String name, tid,email;

    public TeacherHelper() {
    }

    public TeacherHelper(String tid, String name,  String email) {
        this.tid = tid;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
