package com.WizGuys.eStudent.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.model.Assignment;
import com.WizGuys.eStudent.model.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class StudentDetails extends AppCompatActivity {
    private TextView txtSName,txtSEmail, txtSIndex,txtCaAvg,txtExamAvg,txtAss1,txtAss2,sub1,sub2,sub3,txtTotalMarks;
    ImageView imageViewStudent;
    Result result;
    Assignment assignment;
    private void initializeWidgets(){
        txtSName=findViewById(R.id.full_name);
        txtSEmail=findViewById(R.id.grade_email);
        txtSIndex=findViewById(R.id.username_field);
        imageViewStudent=findViewById(R.id.profile_image);
        sub1=findViewById(R.id.txtSub1);
        sub2=findViewById(R.id.txtSub2);
        sub3=findViewById(R.id.txtSub3);
        txtTotalMarks=findViewById(R.id.txtTotal);
        txtExamAvg=findViewById(R.id.student_avg);
        txtCaAvg=findViewById(R.id.assignmentAvg);
        txtAss1=findViewById(R.id.assign1);
        txtAss2=findViewById(R.id.assign2);
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

        result = new Result();

        DatabaseReference readRef2= FirebaseDatabase.getInstance().getReference().child("Result").child(index);
        readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    sub1.setText(snapshot.child("mark1").getValue().toString());
                    sub2.setText(snapshot.child("mark2").getValue().toString());
                    sub3.setText(snapshot.child("mark3").getValue().toString());
                    Integer m1,m2,m3;
                    m1= Integer.parseInt(snapshot.child("mark1").getValue().toString());
                    m2= Integer.parseInt(snapshot.child("mark2").getValue().toString());
                    m3= Integer.parseInt(snapshot.child("mark3").getValue().toString());
                    String total= String.valueOf(getTotalMarks(m1,m2,m3));
                    String avg =String.valueOf(getAvgMarks(m1,m2,m3));

                    txtTotalMarks.setText(total);
                    txtExamAvg.setText(avg);
                }
                else
                    Toast.makeText(getApplicationContext(),"No Marks to Display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         assignment= new Assignment();

        DatabaseReference readRef3= FirebaseDatabase.getInstance().getReference().child("Assignment").child(index);
        readRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    txtAss1.setText(snapshot.child("mark1").getValue().toString());
                    txtAss2.setText(snapshot.child("mark2").getValue().toString());

                    Integer m1,m2;
                    m1= Integer.parseInt(snapshot.child("mark1").getValue().toString());
                    m2= Integer.parseInt(snapshot.child("mark2").getValue().toString());

                    String avg =String.valueOf(getAvgCAMarks(m1,m2));

                    txtCaAvg.setText(avg);
                }
                else
                    Toast.makeText(getApplicationContext(),"No Marks to Display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public float getTotalMarks(float m1,float m2,float m3){
        return (m1+m2+m3);
    }
    public float getAvgMarks(float m1,float m2,float m3){
        return (float) ((m1+m2+m3)/3.0);
    }
    public float getAvgCAMarks(float m1,float m2){
        return (float) ((m1+m2)/2.0);
    }

}