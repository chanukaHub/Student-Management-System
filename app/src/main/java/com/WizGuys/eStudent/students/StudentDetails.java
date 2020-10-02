package com.WizGuys.eStudent.students;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.WizGuys.eStudent.R;

public class StudentDetails extends AppCompatActivity {
    private TextView txtSName,txtSEmail, txtSIndex,txtCaAvg,txtExamAvg,txtAss1,txtAss2,txtMark1,txtMark2,txtMark3,txtTotal;
    ImageView imageViewStudent;
    private void initializeWidgets(){
        txtSName=findViewById(R.id.full_name);
        txtSEmail=findViewById(R.id.grade_email);
        txtSIndex=findViewById(R.id.username_field);
        imageViewStudent=findViewById(R.id.profile_image);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
    }
}