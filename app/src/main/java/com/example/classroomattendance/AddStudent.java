package com.example.classroomattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStudent extends AppCompatActivity {
    private EditText stname, stid;
    private Button add;
    private Button delete;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("students");


        stname = (EditText) findViewById(R.id.stname);
        stid = (EditText) findViewById(R.id.stid);
        add = (Button) findViewById(R.id.addStudentdatabase);
        delete = (Button) findViewById(R.id.deleteStudentdatabase);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });

    }

    public void addStudent() {
        String stnameValue = stname.getText().toString();
        String stidValue = stid.getText().toString();
        if (!TextUtils.isEmpty(stnameValue) && !TextUtils.isEmpty(stidValue)) {
            StudentHelper students = new StudentHelper(stidValue,stnameValue);
            databaseReference.child(stid.getText().toString()).setValue(students);
            stname.setText("");
            stid.setText("");
            Toast.makeText(AddStudent.this, "Student Details Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddStudent.this, "Please Fill Fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteStudent() {
        String stnameValue = stname.getText().toString();
        String stidValue = stid.getText().toString();

        if (!TextUtils.isEmpty(stnameValue) && !TextUtils.isEmpty(stidValue)) {
            databaseReference.child(stid.getText().toString()).removeValue();
            stname.setText("");
            stid.setText("");
            Toast.makeText(AddStudent.this, "Student Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddStudent.this, "Please Fill Fields", Toast.LENGTH_SHORT).show();
        }
    }

}