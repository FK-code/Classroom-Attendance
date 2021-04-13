package com.example.classroomattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText name, stid, email, password, confirmpassword;
    Button register;
    TextView backtologin;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ProgressBar progressBar;
    String email_pattern="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference myRef ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.et_name);
        stid=findViewById(R.id.et_attend_id);
        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_password);
        confirmpassword=findViewById(R.id.et_confirm_password);
        register=findViewById(R.id.btn_register);
        backtologin=findViewById(R.id.back_to_login);
        radioGroup=findViewById(R.id.radioGroup);
        progressBar=findViewById(R.id.progressBar_register);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        stid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailandPassword();
            }
        });

    }

    private void checkEmailandPassword() {
        if (email.getText().toString().matches(email_pattern)){
            if (password.getText().toString().equals(confirmpassword.getText().toString())){
                register.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                checkusertype();

            }else {
                confirmpassword.setError("Password does not match");
            }
        }else {
            email.setError("Invalid Email");
        }

    }

    private void checkusertype() {
        int selectedid=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(selectedid);
        String usertype=radioButton.getText().toString();
        if (usertype.matches("Student") ){
            registerstudent();
        }else {
            registerteacher();
        }
    }

    private void registerteacher() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Registration Successful, Now Login", Toast.LENGTH_SHORT).show();
                            addtodatabaseteacher();
                            addtofirestore();
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            register.setEnabled(true);

                        }

                    }
                });
    }

    private void registerstudent() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Registration Successful, Now Login", Toast.LENGTH_SHORT).show();
                            addtodatabasestudent();
                            addtofirestore();
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            register.setEnabled(true);

                        }

                    }
                });
    }


    private void addtofirestore() {
        Map<Object, String> userdata = new HashMap<>();
        userdata.put("user ID", stid.getText().toString());
        userdata.put("user Name", name.getText().toString());
        userdata.put("user type", radioButton.getText().toString());
        db.collection("users")
                .add(userdata)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("userdata","document added: "+documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("userdata","error adding document ",e);
                    }
                });
    }

    private void addtodatabaseteacher() {
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("teachers");
        //get all values
        String stname =name.getText().toString();
        String stemail =email.getText().toString();
        String userid=stid.getText().toString();

        TeacherHelper teacherHelper=new TeacherHelper(userid,stname,stemail);
        myRef.child(userid).setValue(teacherHelper);
    }

    private void addtodatabasestudent() {
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("students");
        //get all values
        String stname =name.getText().toString();
        String userid=stid.getText().toString();

        StudentHelper studentHelper=new StudentHelper(userid,stname);
        myRef.child(userid).setValue(studentHelper);
    }




    private void checkinputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText()) && password.length() >=8){
                if (!TextUtils.isEmpty(name.getText())){
                    if (!TextUtils.isEmpty(confirmpassword.getText())){
                        if(!TextUtils.isEmpty(stid.getText())){
                            register.setEnabled(true);
                        }else {register.setEnabled(false);

                        }

                    }else {register.setEnabled(false);

                    }
                }else {register.setEnabled(false);

                }

            }else {register.setEnabled(false);

            }

        }else {register.setEnabled(false);

        }
    }
}
