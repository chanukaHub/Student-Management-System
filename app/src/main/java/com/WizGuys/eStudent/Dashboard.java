package com.WizGuys.eStudent;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.helperClass.CategoriesAdapter;
import com.WizGuys.eStudent.helperClass.helperResources.CategoriesHelperClass;
import com.WizGuys.eStudent.helperClass.FeaturedAdapter;
import com.WizGuys.eStudent.helperClass.helperResources.FeaturedHelper;
import com.WizGuys.eStudent.helperClass.MostViewedAdpater;
import com.WizGuys.eStudent.helperClass.helperResources.MostViewedHelperClass;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerView, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;

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
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
 

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

        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xffeff400,0xffaff600});
    }
}
