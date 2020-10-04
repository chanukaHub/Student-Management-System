package com.WizGuys.eStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.model.Assignment;
import com.WizGuys.eStudent.model.Result;
import com.WizGuys.eStudent.students.StudentItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAssignmentResult extends AppCompatActivity {
    EditText txtIndex,assign1,assign2;
    TextView txtName,txtEmail;
    Button btnCheckStudent,btnSubmitResult;
    DatabaseReference dbRef;
    Assignment assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment_result);

        txtIndex=findViewById(R.id.indexAssignEditText);
        txtName=findViewById(R.id.nameAssignTextView);
        txtEmail=findViewById(R.id.emailAssignTextView);
        btnCheckStudent=findViewById(R.id.checkStudentAssignButton);
        btnSubmitResult=findViewById(R.id.submitAssignMarksButton);
        assign1=findViewById(R.id.assign1MarksEditText);
        assign2=findViewById(R.id.assign2MarksEditText);

        assignment =new Assignment();

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
                dbRef= FirebaseDatabase.getInstance().getReference().child("Assignment");
                try {
                    if (TextUtils.isEmpty(txtIndex.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(assign1.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a mark1",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(assign2.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a mark2",Toast.LENGTH_SHORT).show();
                    else{
                        //Take inputs from the user  and assigning them to this instance (std)of the Student...
                        assignment.setIndex(txtIndex.getText().toString().trim());
                        assignment.setMark1(Integer.parseInt(assign1.getText().toString().trim()));
                        assignment.setMark2(Integer.parseInt(assign2.getText().toString().trim()));


                        //Insert in to the database...
                        //dbRef.push().setValue(std);
                        dbRef.child(txtIndex.getText().toString().trim()).setValue(assignment);

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