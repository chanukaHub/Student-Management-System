package com.WizGuys.eStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.model.Assignment;
import com.WizGuys.eStudent.model.Result;
import com.WizGuys.eStudent.model.Student;
import com.WizGuys.eStudent.students.StudentItems;
import com.WizGuys.eStudent.todoList.ToDoForm;
import com.WizGuys.eStudent.todoList.ToDoList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDeleteAssignmentResult extends AppCompatActivity {

    EditText txtIndex,assign1,assign2;
    TextView txtName,txtEmail;
    Button btnCheckStudent,btnSubmitResult,btnDeleteResult;
    DatabaseReference dbRef;
    Student std;
    Assignment assignment;

    private void clearControls(){
        txtEmail.setText("");
        txtName.setText("");
        assign1.setText("");
        assign2.setText("");
    }private void clearMarksControls(){
        assign1.setText("");
        assign2.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_assignment_result);

        txtIndex=findViewById(R.id.udaIndexEditText);
        txtName=findViewById(R.id.udaNameTextView);
        txtEmail=findViewById(R.id.udaEmailTextView);
        btnCheckStudent=findViewById(R.id.udaCheckStudentButton);
        btnSubmitResult=findViewById(R.id.udaSubmitButton);
        btnDeleteResult=findViewById(R.id.udaDeleteButton);
        assign1=findViewById(R.id.udaAssing1MarksEditText);
        assign2=findViewById(R.id.udaAssing2MarksEditText);

        std =new Student();
        assignment =new Assignment();

        btnCheckStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearControls();
                DatabaseReference readRef= FirebaseDatabase.getInstance().getReference().child("Student").child(txtIndex.getText().toString());
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            txtName.setText(snapshot.child("name").getValue().toString());
                            txtEmail.setText(snapshot.child("email").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Invalid index",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                DatabaseReference readRef2= FirebaseDatabase.getInstance().getReference().child("Assignment").child(txtIndex.getText().toString());
                readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            assign1.setText(snapshot.child("mark1").getValue().toString());
                            assign2.setText(snapshot.child("mark2").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Marks to Display",Toast.LENGTH_SHORT).show();
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
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Assignment");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(txtIndex.getText().toString())){
                            try {
                                assignment.setIndex(txtIndex.getText().toString().trim());
                                assignment.setMark1(Integer.parseInt(assign1.getText().toString().trim()));
                                assignment.setMark2(Integer.parseInt(assign2.getText().toString().trim()));


                                dbRef = FirebaseDatabase.getInstance().getReference().child("Assignment").child(txtIndex.getText().toString());
                                dbRef.setValue(assignment);
                                openImagesActivity();

                                Toast.makeText(getApplicationContext(),"Data update successfully",Toast.LENGTH_SHORT).show();
                            }catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid Marks",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Result to Update",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });//end update

        btnDeleteResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteAssignmentResult.this);

                builder.setMessage("Do you want to delete marks?");
                builder.setTitle("Please Confirm");
                //user needs select choice
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DatabaseReference delRef =FirebaseDatabase.getInstance().getReference().child("Assignment");
                        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(txtIndex.getText().toString())){
                                    dbRef= FirebaseDatabase.getInstance().getReference().child("Assignment").child(txtIndex.getText().toString());
                                    dbRef.removeValue();
                                    clearMarksControls();
                                    Toast.makeText(getApplicationContext(),"Result Deleted Successfully",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"No Result to delete",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dialogInterface.cancel();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                //create alert dialog
                AlertDialog alertDialog = builder.create();

                //show alert dialog
                alertDialog.show();



            }
        });//end delete
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, StudentItems.class);
        startActivity(intent);
    }
}