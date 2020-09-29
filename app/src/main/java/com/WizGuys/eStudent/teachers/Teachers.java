package com.WizGuys.eStudent.teachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.TeachersAdapter;
import com.WizGuys.eStudent.model.TeachersObj;
import com.google.firebase.database.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
 import com.google.firebase.database.ValueEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Teachers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView textView;
    Menu menu;
    Button bRegister;
    NavigationView navigationView;
    ImageButton imageButton;
    Firebase mRef;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("Lectures");

    FloatingActionButton buttonAdd;
    RecyclerView recyclerView;
    private List<TeachersObj> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imageButton = findViewById(R.id.imageView);
        toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navi, R.string.close_navi);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_message);

        bRegister = (Button) findViewById(R.id.button8);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teachers.this, TeachersProfile.class);
                startActivity(intent);
            }
        });

        loadTeachers();
    }

    private void loadTeachers() {
        list.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TeachersObj value = snapshot.getValue(TeachersObj.class);
                    list.add(value);
                }
                recyclerView.setAdapter(new TeachersAdapter(Teachers.this,list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("TAG","Value is",databaseError.toException());
            }

        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



}