package com.WizGuys.eStudent;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.helperClass.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  {

    Button bLogin;
    TextView tvRegisterLink;
    EditText etEmail, etPassword;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mauthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword1);
        bLogin = (Button) findViewById(R.id.bLogin);
       // tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        mauthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(Login.this, Dashboard.class));
                }
            }
        };

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mauthStateListener);
    }

    private void startSignIn() {
         final String email = etEmail.getText().toString();
         String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Fields are Empty", Toast.LENGTH_LONG).show();
        }else {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "Invalid Login", Toast.LENGTH_LONG).show();
                            }else {
                                Common.email = email;
                                startActivity(new Intent(Login.this, Dashboard.class));
                            }
                        }
                    });
        }
    }
}