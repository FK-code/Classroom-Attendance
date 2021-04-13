package com.example.classroomattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class TakeAttendance extends AppCompatActivity {
    private TextView todaysdate,coursename;
    private EditText stid;
    private Button btn_attendance;
    FirebaseDatabase databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        todaysdate = findViewById(R.id.todaysdate);
        coursename = findViewById(R.id.coursename);
        stid = findViewById(R.id.stid);
        btn_attendance = findViewById(R.id.btn_attendance);


        databaseReference = FirebaseDatabase.getInstance();
        //set date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = day + "-" + month + "-" + year;
        todaysdate.setText(date);

        //set course name
        Intent intent = getIntent();
        coursename.setText(intent.getStringExtra("coursename"));

        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeattendance();

            }
        });
    }

    private void takeattendance() {
        databaseReference.getReference("students").child(stid.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            databaseReference.getReference("attendance")
                                    .child(coursename.getText().toString())
                                    .child("Date= "+todaysdate.getText().toString())
                                    .child(stid.getText().toString())
                                    .setValue(dataSnapshot.getValue());
                            stid.setText("");
                            Toast.makeText(TakeAttendance.this, "Attendance accepted", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(TakeAttendance.this, "ID Invalid", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

}
