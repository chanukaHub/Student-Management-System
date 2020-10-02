package com.WizGuys.eStudent.teachers;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Detail extends AppCompatActivity {

    private TextView nameDetailTextView,descriptionDetailTextView, techEmail,techAddress,
            techContact,techSalary,
            techQualification;
    ImageView teacherDetailImageView;
    private void initializeWidgets(){
        nameDetailTextView= findViewById(R.id.teName);
        descriptionDetailTextView= findViewById(R.id.teDescription);
        techAddress= findViewById(R.id.teAddress);
        techContact= findViewById(R.id.teContact);
        techEmail= findViewById(R.id.teEmail);
        techQualification= findViewById(R.id.teQualification);
//      dateDetailTextView= findViewById(R.id.dateDetailTextView);
        techSalary= findViewById(R.id.teSalary);
        teacherDetailImageView=findViewById(R.id.teacherDetailImageView);
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializeWidgets();

        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");
        String address=i.getExtras().getString("ADDRESS_KEY");
        String contact=i.getExtras().getString("CONTACT_KEY");
        String description=i.getExtras().getString("DESCRIPTION_KEY");
        String qualification=i.getExtras().getString("QUALIFICATION_KEY");
        String salary=i.getExtras().getString("SALARY_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");


        nameDetailTextView.setText("Name : "+name);
        techAddress.setText("Address : "+address);
        techEmail.setText("Email : "+email);
        techContact.setText("Contact : "+contact);
        techSalary.setText("Salary : "+salary);
        techQualification.setText("Qualification : "+qualification);
        descriptionDetailTextView.setText("Description : "+description);

        Picasso.with(this)
                .load(imageURL)
                .placeholder(R.drawable.capture1)
                .fit()
                .centerCrop()
                .into(teacherDetailImageView);
    }
}