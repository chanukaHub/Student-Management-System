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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.helperClass.CategoriesAdapter;
import com.WizGuys.eStudent.helperClass.FeaturedAdapter;
import com.WizGuys.eStudent.helperClass.MostViewedAdpater;
import com.WizGuys.eStudent.helperClass.helperResources.CategoriesHelperClass;
import com.WizGuys.eStudent.helperClass.helperResources.FeaturedHelper;
import com.WizGuys.eStudent.helperClass.helperResources.MostViewedHelperClass;
import com.WizGuys.eStudent.notice.Notice;
import com.WizGuys.eStudent.teachers.Teachers;
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

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        //Hooks
        recyclerView = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menu_Icon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        btnLogOut = findViewById(R.id.imgLogOut);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, Login.class));
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
    private void categoriesRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.capture1, "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.capture4, "HOSPITAL"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.capture4, "Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient4, R.drawable.capture4, "Shopping"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.capture4, "Transport"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.capture4,"333333333333", "McDonald's"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.capture4, "33333333332","Edenrobe"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.capture4, "J.","323"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.capture4, "Walmart","43322"));

        adapter = new MostViewedAdpater(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }
    private void featuredRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<FeaturedHelper> featuredHelperArrayList = new ArrayList<>();
        featuredHelperArrayList.add(new FeaturedHelper(R.drawable.images, "mcDonal", "666666666666666666666666666666666666"));
        featuredHelperArrayList.add(new FeaturedHelper(R.drawable.capture1, "gfdgf", "666666666666666666666666666666666666"));
        featuredHelperArrayList.add(new FeaturedHelper(R.drawable.capture4, "545345", "666666666666666666666666666666666666"));
        adapter = new FeaturedAdapter(featuredHelperArrayList);
        recyclerView.setAdapter(adapter);

        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                break;
            case R.id.nav_message1:
                Intent intent = new Intent(Dashboard.this, MainActivity4.class);
                startActivity(intent);
                break;
            case R.id.nav_message3:
                Intent intent1 = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_share:
                Intent intent3 = new Intent(Dashboard.this, Teachers.class);
                startActivity(intent3);
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
