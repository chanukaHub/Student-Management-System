package com.WizGuys.eStudent.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.WizGuys.eStudent.R;
import com.squareup.picasso.Picasso;

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
        initializeWidgets();

        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String index=i.getExtras().getString("INDEX_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");

        txtSName.setText(name);
        txtSIndex.setText(index);
        txtSEmail.setText(email);
        Picasso.with(this)
                .load(imageURL)
                .placeholder(R.drawable.capture1)
                .fit()
                .centerCrop()
                .into(imageViewStudent);

    }
}