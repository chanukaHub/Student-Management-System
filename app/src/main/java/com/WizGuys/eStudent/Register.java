package com.WizGuys.eStudent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.client.Firebase;



public class Register extends AppCompatActivity  {

    Button bRegister;
    private Firebase mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://e-student-ff4d9.firebaseio.com/");
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mc = mRef.child("Name");
                mc.setValue("Kamalata1");
            }
        });

    }

}