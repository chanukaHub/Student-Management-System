package com.WizGuys.eStudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.WizGuys.eStudent.teachers.Items;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.WizGuys.eStudent.teachers.Upload;

public class ResultDashboard extends AppCompatActivity {
    private Button openResultActivityBtn,openUploadDeleteResultActivityBtn,openAssgnActivityBtn,openUploadDeleteAssignActivityBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dashboard);

        openResultActivityBtn = findViewById ( R.id.openAddResultActivityBtn );
        openAssgnActivityBtn = findViewById ( R.id.openAddAssignResultActivityBtn );
        openUploadDeleteResultActivityBtn= findViewById(R.id.openUpdateDeleteResultActivityBtn);
        openUploadDeleteAssignActivityBtn=findViewById(R.id.openUpdateDeleteAssignResultActivityBtn);
        openResultActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultDashboard.this, AddResult.class);
                startActivity(i);
            }
        });
        openAssgnActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultDashboard.this, AddAssignmentResult.class);
                startActivity(i);
            }
        });
        openUploadDeleteResultActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultDashboard.this, UpdateDeleteResult.class);
                startActivity(i);
            }
        });
        openUploadDeleteAssignActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultDashboard.this, UpdateDeleteAssignmentResult.class);
                startActivity(i);
            }
        });
    }
}