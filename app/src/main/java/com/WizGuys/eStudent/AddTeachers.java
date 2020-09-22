package com.WizGuys.eStudent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddTeachers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView txtName, address, description, email, qualification, contact, salary;
    Button btnSubmit;
    Menu menu;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        btnSubmit = findViewById(R.id.btnLectureSubmit);
        txtName = findViewById(R.id.etLectureName);
        address = findViewById(R.id.etLectureAddress);
        description = findViewById(R.id.etLectureDis);
        email = findViewById(R.id.etLectureEmail);
        qualification = findViewById(R.id.etLectureQuli);
        contact = findViewById(R.id.etContactNo);
        salary = findViewById(R.id.etSalary);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navi, R.string.close_navi);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_message);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Lectures");
                String key = myRef.push().getKey();

                map.put("id", key);
                map.put("name", txtName.getText().toString());
                map.put("address", address.getText().toString());
                map.put("description", description.getText().toString());
                map.put("email", email.getText().toString());
                map.put("qualification", qualification.getText().toString());
                map.put("contact", contact.getText().toString());
                map.put("salary", salary.getText().toString());


                myRef.child(key).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                txtName.setText("");
                                address.setText("");
                                description.setText("");
                                qualification.setText("");
                                contact.setText("");
                                email.setText("");
                                salary.setText("");
                                Toast.makeText(getApplicationContext(), "Insert Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Insert Error", Toast.LENGTH_LONG).show();
                    }
                });
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