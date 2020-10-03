package com.WizGuys.eStudent.subjects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.SubjectAdapter;
import com.WizGuys.eStudent.adapter.ToDoAdapter;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Subject;
import com.WizGuys.eStudent.model.Task;
import com.WizGuys.eStudent.todoList.UpdateToDo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class SubjectList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SubjectAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Subject> mSubjects;

    private ImageView addTaskImgToDo;


    private void updateActivity(String[] data){
        Intent intent = new Intent(this, UpdateToDo.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("NAME_KEY",data[1]);
        intent.putExtra("CHAPTER_KEY",data[2]);
        intent.putExtra("INFO_KEY",data[3]);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);


        mRecyclerView = findViewById(R.id.subject_list_recy);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progressBarSubj);
        mProgressBar.setVisibility(View.VISIBLE);

        mSubjects = new ArrayList<>();
        mAdapter = new SubjectAdapter(SubjectList.this, mSubjects);
        mRecyclerView.setAdapter(mAdapter);
        //mAdapter.setOnItemClickListener(SubjectList.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Common.SUBJECTS_TABLE);
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSubjects.clear();
                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    Subject upload = subjectSnapshot.getValue(Subject.class);
                    upload.setId(subjectSnapshot.getKey());
                    mSubjects.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SubjectList.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        //addButton
        addTaskImgToDo = findViewById(R.id.add_subject_button);

        addTaskImgToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubjectList.this, AddSubject.class);
                startActivity(intent);
            }
        });
    }
}