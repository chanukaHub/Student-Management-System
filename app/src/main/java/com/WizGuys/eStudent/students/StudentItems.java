package com.WizGuys.eStudent.students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.StudentAdapter;
import com.WizGuys.eStudent.model.Student;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StudentItems extends AppCompatActivity implements StudentAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private StudentAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Student> mStudents;
    private Button addStudentBtn;
    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, StudentDetails.class);
        intent.putExtra("NAME_KEY",data[0]);
        intent.putExtra("INDEX_KEY",data[1]);
        intent.putExtra("EMAIL_KEY",data[2]);
        intent.putExtra("IMAGE_KEY",data[3]);
        startActivity(intent);
    }

    private void updateActivity(String[] data){
        Intent intent = new Intent(this, StudentUpdate.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("NAME_KEY",data[1]);
        intent.putExtra("INDEX_KEY",data[2]);
        intent.putExtra("ADDRESS_KEY",data[3]);
        intent.putExtra("CONTACT_KEY",data[4]);
        intent.putExtra("EMAIL_KEY",data[5]);
        intent.putExtra("IMAGE_KEY",data[6]);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_items);
        addStudentBtn =findViewById(R.id.student_add_btn);
        mRecyclerView = findViewById(R.id.mStudentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.myStudentDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mStudents = new ArrayList<>();
        mAdapter = new StudentAdapter (StudentItems.this, mStudents);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(StudentItems.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Student");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mStudents.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Student student = studentSnapshot.getValue(Student.class);
                    student.setKey(studentSnapshot.getKey());
                    mStudents.add(student);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StudentItems.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentItems.this,StudentAdd.class);
                startActivity(intent);
            }
        });
    }
    public void onItemClick(int position) {
        Student clickedStudent=mStudents.get(position);
        String[] studentData={clickedStudent.getName(),
                clickedStudent.getIndex(),clickedStudent.getEmail(),
                clickedStudent.getImageURL()
        };

        openDetailActivity(studentData);
    }
    @Override
    public void onShowItemClick(int position) {
        Student clickedStudent=mStudents.get(position);
        final String selectedKey = clickedStudent.getKey();
        String[] studentData={selectedKey,clickedStudent.getName(),
                clickedStudent.getIndex(),clickedStudent.getAddress(),
                clickedStudent.getContact(),clickedStudent.getEmail(),
                clickedStudent.getImageURL()
        };
        updateActivity(studentData);
    }
    @Override
    public void onDeleteItemClick(int position) {
        Student selectedItem = mStudents.get(position);
        final String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageURL());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(StudentItems.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}