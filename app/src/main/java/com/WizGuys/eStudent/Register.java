package com.WizGuys.eStudent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;

public class Register extends AppCompatActivity {
    Button bRegister;
    EditText etName, etAge, etUserName, etPassword;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://student-management-syste-970d0.firebaseio.com/");
       bRegister = findViewById(R.id.addBtn);

       bRegister.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               Firebase mRefChild = mRef.child("Name");
               mRefChild.setValue("Rivindu");
           }
       });
    }




}