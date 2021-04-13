package com.example.classroomattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourseSelect extends AppCompatActivity {
    Button course1,course2,course3,course4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);
        course1=findViewById(R.id.btn_course1);
        course2=findViewById(R.id.btn_course2);
        course3=findViewById(R.id.btn_course3);
        course4=findViewById(R.id.btn_course4);

        course1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TakeAttendance.class);
                intent.putExtra("coursename",course1.getText().toString());
                startActivity(intent);
            }
        });
        course2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TakeAttendance.class);
                intent.putExtra("coursename",course2.getText().toString());
                startActivity(intent);
            }
        });
        course3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TakeAttendance.class);
                intent.putExtra("coursename",course3.getText().toString());
                startActivity(intent);
            }
        });
        course4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TakeAttendance.class);
                intent.putExtra("coursename",course4.getText().toString());
                startActivity(intent);
            }
        });
    }
}
