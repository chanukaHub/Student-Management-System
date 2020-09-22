package com.WizGuys.eStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.WizGuys.eStudent.teachers.Teachers;
import com.WizGuys.eStudent.teachers.TeachersProfile;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.navigation.NavigationView;

import java.util.Map;

public class AddTeachers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView txtName,txtNamea;
    Button btnSubmit;
    Menu menu;
    NavigationView navigationView;
    Firebase  mRef = new Firebase("https://mobileapp-41e97.firebaseio.com/Name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnSubmit = findViewById(R.id.button);
        txtName = findViewById(R.id.editTextTextPersonName2);
        txtNamea = findViewById(R.id.editTextTextPersonName5);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navi, R.string.close_navi);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_message);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*String value = dataSnapshot.getValue(String.class);
                txtNamea.setText(value);*/

                Map<String,String> map = dataSnapshot.getValue(Map.class);
                String name = map.get("Name");
                String age = map.get("Age");
                Log.v("E_VALUE","Name : "+name);
                Log.v("E_VALUE","Age : "+name);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //   Firebase mc = mRef.child("Name");
                String name = txtName.getText().toString();
                mRef.push().setValue(name);
            }
        });
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}