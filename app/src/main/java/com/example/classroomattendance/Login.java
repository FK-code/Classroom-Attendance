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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email,password;
    Button btnlogin;
    TextView toregister,forgotpassowrd;
    ProgressBar progressBar;
    String email_pattern="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_password);
        toregister=findViewById(R.id.btn_toregister);
        progressBar=findViewById(R.id.progressBar_login);
        btnlogin=findViewById(R.id.btn_login);

        mAuth=FirebaseAuth.getInstance();

        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        
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
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailandpassword();
            }
        });
    }

    private void checkEmailandpassword() {
        if (email.getText().toString().matches(email_pattern)){
            if (password.length() >= 8){
                btnlogin.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    btnlogin.setEnabled(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                                btnlogin.setEnabled(true);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });

            }else {
                password.setError("Invalid password");
            }
        }else {
            email.setError("Invalid Email");
        }

    }

    private void checkinputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText())){
                btnlogin.setEnabled(true);
            }else {
                btnlogin.setEnabled(false);
            }
        }else {
            btnlogin.setEnabled(false);
        }
    }
}
