package com.WizGuys.eStudent.todoList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.TeachersAdapter;
import com.WizGuys.eStudent.adapter.ToDoAdapter;
import com.WizGuys.eStudent.model.Task;
import com.WizGuys.eStudent.model.Teacher;
import com.WizGuys.eStudent.teachers.Detail;
import com.WizGuys.eStudent.teachers.Items;
import com.WizGuys.eStudent.teachers.Update;
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

public class ToDoList extends AppCompatActivity implements ToDoAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ToDoAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Task> mTasks;

    private ImageView addTaskImgToDo;


    private void updateActivity(String[] data){
        Intent intent = new Intent(this, UpdateToDo.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("NAME_KEY",data[1]);
        intent.putExtra("DATE_KEY",data[2]);
        intent.putExtra("STATE_KEY",data[3]);
        intent.putExtra("EMAIL_KEY",data[4]);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_to_do_list );

        mRecyclerView = findViewById(R.id.task_list_ToDo);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progressbar_ToDo);
        mProgressBar.setVisibility(View.VISIBLE);

        mTasks = new ArrayList<> ();
        mAdapter = new ToDoAdapter (ToDoList.this, mTasks);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ToDoList.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Task");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTasks.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task upload = taskSnapshot.getValue(Task.class);
                    upload.setTaskKey(taskSnapshot.getKey());
                    mTasks.add(upload);
                }
                mAdapter.notifyDataSetChanged();
//                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ToDoList.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //     mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        //addButton
        addTaskImgToDo = findViewById(R.id.add_task_img_ToDo);

        addTaskImgToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoList.this, ToDoForm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onShowItemClick(int position) {
        Task task1=mTasks.get(position);
        final String selectedKey = task1.getTaskKey();
        String[] teacherData={
                selectedKey,
                task1.getTask(),
                task1.getDate(),
                task1.getState(),
                task1.getUserEmail()

        };

        updateActivity(teacherData);
    }
    @Override
    public void onDeleteItemClick(int position) {
        Task selectedItem = mTasks.get(position);
        final String selectedKey = selectedItem.getTaskKey();

        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(ToDoList.this, "Item deleted", Toast.LENGTH_SHORT).show();


    }
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}