package com.example.classroomattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DynamicCourse extends AppCompatActivity {
    RecyclerView recyclerView;
    CourseAdapter courseAdapter;
    List<Course>courseList;
    EditText newcoursename;
    Button addcourse;
    DatabaseReference reference;
    TextView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_course);


        recyclerView=findViewById(R.id.recylerview_courselist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addcourse=findViewById(R.id.btn_addcourse);
        newcoursename=findViewById(R.id.et_coursename);
        refresh=findViewById(R.id.btn_refresh);


        reference = FirebaseDatabase.getInstance().getReference("courses");


        addcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcoursetodatabase();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshlist();
            }
        });


        newcoursename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(newcoursename.getText().toString())){
                    addcourse.setEnabled(false);
                }else {
                    addcourse.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(newcoursename.getText().toString())){
                    addcourse.setEnabled(false);
                }else {
                    addcourse.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        courseList=new ArrayList<>();
        courseAdapter=new CourseAdapter(DynamicCourse.this,courseList);
        recyclerView.setAdapter(courseAdapter);


    }

    private void addcoursetodatabase() {
        Course course=new Course(newcoursename.getText().toString());
        reference.child(newcoursename.getText().toString()).setValue(course)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){
                            Toast.makeText(DynamicCourse.this, "Course Added", Toast.LENGTH_SHORT).show();
                            newcoursename.setText("");
                        }
                        else {
                            String error=task.getException().getMessage();
                            Toast.makeText(DynamicCourse.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    private void refreshlist() {
        courseList.clear();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot courseSnapshot:dataSnapshot.getChildren()){
                        Course course=courseSnapshot.getValue(Course.class);
                        courseList.add(course);
                    }
                    courseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
