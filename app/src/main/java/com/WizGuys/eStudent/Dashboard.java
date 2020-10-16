package com.WizGuys.eStudent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.finance.FinanceDashboard;
import com.WizGuys.eStudent.helperClass.CategoriesAdapter;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.helperClass.FeaturedAdapter;
import com.WizGuys.eStudent.helperClass.MostViewedAdpater;
import com.WizGuys.eStudent.helperClass.helperResources.CategoriesHelperClass;
import com.WizGuys.eStudent.helperClass.helperResources.FeaturedHelper;
import com.WizGuys.eStudent.helperClass.helperResources.MostViewedHelperClass;
import com.WizGuys.eStudent.students.StudentItems;
import com.WizGuys.eStudent.subjects.SubjectList;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.WizGuys.eStudent.timetables.TimeTablesDashboard;
import com.WizGuys.eStudent.todoList.ToDoList;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;
    RecyclerView recyclerView, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menu_Icon,btnLogOut;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //todo list button

    RelativeLayout todoButton,teachersDashboard;
    //subjectButton
    RelativeLayout subjectButton,studentButton,resultButton,timetableButton,financeButton;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        //Hooks
        recyclerView = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        menu_Icon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        btnLogOut = findViewById(R.id.imgLogOut);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //todo list button
        todoButton = findViewById(R.id.todoButton);
        teachersDashboard = findViewById(R.id.teachersDashboard);

        //studentButton
        studentButton =findViewById(R.id.studentButton);

        //resultButton
        resultButton = findViewById(R.id.resultButton);

        //timetableButton
        timetableButton = findViewById(R.id.timetableBtn);

        //subjectButton
        subjectButton = findViewById(R.id.subjectManager);

        //financeButton
        financeButton = findViewById(R.id.financeBtn);




        navigationDrawer();

        featuredRecycler();
        mostViewedRecycler();


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Common.email = " ";
                startActivity(new Intent(Dashboard.this, Login.class));
            }
        });

        //todo button onclick listener
        todoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ToDoList.class);
                startActivity(intent);
            }
        });
        teachersDashboard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, TeachersDashboard.class);
                startActivity(intent);
            }
        });

        //subjectButton onclick listener
        subjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, SubjectList.class);

                startActivity(intent);
            }
        });
        studentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, StudentItems.class);
                startActivity(intent);
            }
        });
        resultButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ResultDashboard.class);
                startActivity(intent);
            }
        });
        timetableButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, TimeTablesDashboard.class);
                startActivity(intent);
            }
        });
        financeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, FinanceDashboard.class);
                startActivity(intent);
            }
        });

    }

    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_message);

        menu_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigationDrawer();
    }
    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(
                R.color.colorYellow
        ));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset){

                final float diffScaleOffset = slideOffset* (1- END_SCALE);
                final float offSetScale = 1 - diffScaleOffset;
                contentView.setScaleX(offSetScale);
                contentView.setScaleX(offSetScale);


                final float xOffset = drawerLayout.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaleOffset/2;
                final float xTranslation =xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.bell1,"Final Exam 2020", "Final examination will be started on 7th November"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.bell1, "Study Leaves","Study leaves can be taken after 26th October"));


        adapter = new MostViewedAdpater(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }
    private void featuredRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<FeaturedHelper> featuredHelperArrayList = new ArrayList<>();
        featuredHelperArrayList.add(new FeaturedHelper(R.drawable.q1, "Mansa", "CEO at e-Students System"));
        featuredHelperArrayList.add(new FeaturedHelper(R.drawable.q2, "Alisa", "Co-founder"));
        featuredHelperArrayList.add(new FeaturedHelper(R.drawable.q3, "Sachini", "Senior Lecturer"));
        adapter = new FeaturedAdapter(featuredHelperArrayList);
        recyclerView.setAdapter(adapter);

        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                break;
            case R.id.nav_Payment:
                Intent intent = new Intent(Dashboard.this, FinanceDashboard.class);
                startActivity(intent);
                break;
            case R.id.nav_message3:
                Intent intent1 = new Intent(Dashboard.this, TimeTablesDashboard.class);
                startActivity(intent1);
                break;
            case R.id.nav_share:
                Intent intent3 = new Intent(Dashboard.this, TeachersDashboard.class);
                startActivity(intent3);
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;



        }
        return true;
    }
}