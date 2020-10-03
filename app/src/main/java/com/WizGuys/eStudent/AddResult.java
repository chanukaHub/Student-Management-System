package com.WizGuys.eStudent;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.model.Result;
import com.WizGuys.eStudent.model.Student;
import com.WizGuys.eStudent.students.StudentItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddResult extends AppCompatActivity {
    EditText txtIndex,sub1,sub2,sub3;
    TextView txtName,txtEmail;
    Button btnCheckStudent,btnSubmitResult;
    DatabaseReference dbRef;
    Student std;
    Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);

        txtIndex=findViewById(R.id.indexEditText);
        txtName=findViewById(R.id.studentNameTextView);
        txtEmail=findViewById(R.id.studentEmailTextView);
        btnCheckStudent=findViewById(R.id.checkStudentButton);
        btnSubmitResult=findViewById(R.id.resultSubmitButton);
        sub1=findViewById(R.id.sub1_marks);
        sub2=findViewById(R.id.sub2_marks);
        sub3=findViewById(R.id.sub3_marks);

        //std =new Student();
        result =new Result();

        btnCheckStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("Student").child(txtIndex.getText().toString());
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            txtName.setText(snapshot.child("name").getValue().toString());
                            txtEmail.setText(snapshot.child("email").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Display",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });//end show

        btnSubmitResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef= FirebaseDatabase.getInstance().getReference().child("Result");
                try {
                    if (TextUtils.isEmpty(txtIndex.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(sub1.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a mark1",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(sub2.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a mark2",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(sub3.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a mark3",Toast.LENGTH_SHORT).show();
                    else{
                        //Take inputs from the user  and assigning them to this instance (std)of the Student...
                        result.setIndex(txtIndex.getText().toString().trim());
                        result.setMark1(Integer.parseInt(sub1.getText().toString().trim()));
                        result.setMark2(Integer.parseInt(sub2.getText().toString().trim()));
                        result.setMark3(Integer.parseInt(sub3.getText().toString().trim()));

                        //Insert in to the database...
                        //dbRef.push().setValue(std);
                        dbRef.child(txtIndex.getText().toString().trim()).setValue(result);

                        //feedback to the user via a Toast...
                        Toast.makeText(getApplicationContext(),"Data saved Successfully",Toast.LENGTH_SHORT).show();
                        openImagesActivity();
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Marks",Toast.LENGTH_SHORT).show();
                }
            }
        });//end save
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, StudentItems.class);
        startActivity(intent);
    }


}