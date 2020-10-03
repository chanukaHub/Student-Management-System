package com.WizGuys.eStudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.model.Result;
import com.WizGuys.eStudent.model.Student;
import com.WizGuys.eStudent.students.StudentItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDeleteResult extends AppCompatActivity {
    EditText txtIndex,sub1,sub2,sub3;
    TextView txtName,txtEmail;
    Button btnCheckStudent,btnSubmitResult,btnDeleteResult;
    DatabaseReference dbRef;
    Student std;
    Result result;

    private void clearControls(){
        txtEmail.setText("");
        txtName.setText("");
        sub1.setText("");
        sub2.setText("");
        sub3.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_result);

        txtIndex=findViewById(R.id.udrIndexEditText);
        txtName=findViewById(R.id.udrNameTexView);
        txtEmail=findViewById(R.id.udrEmailTexView);
        btnCheckStudent=findViewById(R.id.udrCheckStudentButton);
        btnSubmitResult=findViewById(R.id.udrUpdateResultButton);
        btnDeleteResult=findViewById(R.id.udrDeleteResultButton);
        sub1=findViewById(R.id.udrsub1_marks);
        sub2=findViewById(R.id.udrsub2_marks);
        sub3=findViewById(R.id.udrsub3_marks);

        std =new Student();
        result =new Result();

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
                DatabaseReference readRef2= FirebaseDatabase.getInstance().getReference().child("Result").child(txtIndex.getText().toString());
                readRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            sub1.setText(snapshot.child("mark1").getValue().toString());
                            sub2.setText(snapshot.child("mark2").getValue().toString());
                            sub3.setText(snapshot.child("mark3").getValue().toString());
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
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Result");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(txtIndex.getText().toString())){
                            try {
                                result.setIndex(txtIndex.getText().toString().trim());
                                result.setMark1(Integer.parseInt(sub1.getText().toString().trim()));
                                result.setMark2(Integer.parseInt(sub2.getText().toString().trim()));
                                result.setMark3(Integer.parseInt(sub3.getText().toString().trim()));

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Result").child(txtIndex.getText().toString());
                                dbRef.setValue(result);
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
                final DatabaseReference delRef =FirebaseDatabase.getInstance().getReference().child("Result");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(txtIndex.getText().toString())){
                            dbRef= FirebaseDatabase.getInstance().getReference().child("Result").child(txtIndex.getText().toString());
                            dbRef.removeValue();
                            clearControls();
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
            }
        });//end delete
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, StudentItems.class);
        startActivity(intent);
    }
}