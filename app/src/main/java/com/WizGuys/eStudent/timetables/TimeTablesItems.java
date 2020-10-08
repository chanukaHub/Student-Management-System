package com.WizGuys.eStudent.timetables;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.TeachersAdapter;
import com.WizGuys.eStudent.adapter.TimeTableAdapter;
import com.WizGuys.eStudent.model.Teacher;
import com.WizGuys.eStudent.model.TimeTable;
import com.WizGuys.eStudent.teachers.Detail;

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


public class TimeTablesItems extends AppCompatActivity implements TimeTableAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private TimeTableAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<TimeTable> mTimeTable;
    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("DATE",data[0]);
        intent.putExtra("GRADE",data[1]);
        intent.putExtra("Time8",data[2]);
        intent.putExtra("Time10",data[3]);
        intent.putExtra("Time12",data[4]);
        intent.putExtra("Time2",data[5]);


        startActivity(intent);
    }

    private void updateActivity(String[] data){
        Intent intent = new Intent(this, TimeUpdate.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("DATE",data[1]);
        intent.putExtra("GRADE",data[2]);
        intent.putExtra("Time8",data[3]);
        intent.putExtra("Time10",data[4]);
        intent.putExtra("Time12",data[5]);
        intent.putExtra("Time2",data[6]);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_items );
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mTimeTable = new ArrayList<> ();
        mAdapter = new TimeTableAdapter(TimeTablesItems.this, mTimeTable);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(TimeTablesItems.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("save_timeTable");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTimeTable.clear();
                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    TimeTable upload = teacherSnapshot.getValue(TimeTable.class);
                    upload.setId(teacherSnapshot.getKey());
                    mTimeTable.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TimeTablesItems.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void onItemClick(int position) {
        TimeTable clickedTeacher=mTimeTable.get(position);
        String[] teacherData={clickedTeacher.getGrade(),
                clickedTeacher.getDate(),
                clickedTeacher.getTime8_10(),
                clickedTeacher.getTime10_12(),clickedTeacher.getTime12_2(),
                clickedTeacher.getTime2_4()
        };

        openDetailActivity(teacherData);
    }
    @Override
    public void onShowItemClick(int position) {
        TimeTable clickedTeacher=mTimeTable.get(position);
        final String selectedKey = clickedTeacher.getId();
        String[] teacherData={selectedKey,clickedTeacher.getGrade(),
                clickedTeacher.getDate(),clickedTeacher.getTime8_10(),
                clickedTeacher.getTime10_12(),clickedTeacher.getTime12_2(),
                clickedTeacher.getTime2_4()
        };
        updateActivity(teacherData);
    }
    @Override
    public void onDeleteItemClick(int position) {
        TimeTable selectedItem = mTimeTable.get(position);
        final String selectedKey = selectedItem.getId();


        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(TimeTablesItems.this, "Item deleted", Toast.LENGTH_SHORT).show();


    }
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}