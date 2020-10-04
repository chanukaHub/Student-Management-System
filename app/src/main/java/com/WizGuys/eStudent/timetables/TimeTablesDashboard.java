package com.WizGuys.eStudent.timetables;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.WizGuys.eStudent.Dashboard;
import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.WizGuys.eStudent.timetables.TimeTables;

public class TimeTablesDashboard extends AppCompatActivity {


    Button viewAll,addTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_dashboard);

        viewAll = findViewById(R.id.timeviewAll);
        addTable = findViewById(R.id.timeaddTimeTable);


        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(TimeTablesDashboard.this, TimeTables.class);
                startActivity(intent3);
            }
        });

        addTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(TimeTablesDashboard.this, TimeTables.class);
                startActivity(intent3);
            }
        });
    }
}
