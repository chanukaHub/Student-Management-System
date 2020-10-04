package com.WizGuys.eStudent.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.FinanceAdapter;
import com.WizGuys.eStudent.adapter.TeachersAdapter;
import com.WizGuys.eStudent.model.Finance;
import com.WizGuys.eStudent.model.Task;
import com.WizGuys.eStudent.model.Teacher;
import com.WizGuys.eStudent.teachers.Detail;
import com.WizGuys.eStudent.teachers.Update;
import com.WizGuys.eStudent.todoList.ToDoList;
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


public class AllFinance extends AppCompatActivity implements FinanceAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private FinanceAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Finance> mFinance;

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("NAME_KEY",data[0]);
        intent.putExtra("EMAIL_KEY",data[1]);
        intent.putExtra("AMOUNT_KEY",data[2]);
        intent.putExtra("BALANCE_KEY",data[3]);
        intent.putExtra("DU_DATE_KEY",data[4]);
        intent.putExtra("DATE_KEY",data[5]);
        intent.putExtra("DESCRIPTION_KEY",data[6]);
        startActivity(intent);
    }

    private void updateActivity(String[] data){
        Intent intent = new Intent(this, UpdateFinance.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("NAME_KEY",data[1]);
        intent.putExtra("EMAIL_KEY",data[2]);
        intent.putExtra("AMOUNT_KEY",data[3]);
        intent.putExtra("BALANCE_KEY",data[4]);
        intent.putExtra("DU_DATE_KEY",data[5]);
        intent.putExtra("DATE_KEY",data[6]);
        intent.putExtra("DESCRIPTION_KEY",data[7]);

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
        mFinance = new ArrayList<> ();
        mAdapter = new FinanceAdapter (AllFinance.this, mFinance);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(AllFinance.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("finance_uploads");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFinance.clear();
                for (DataSnapshot finance : dataSnapshot.getChildren()) {
                    Finance upload = finance.getValue(Finance.class);
                    upload.setKey(finance.getKey());
                    mFinance.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AllFinance.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDeleteItemClick(int position) {
        Finance selectedItem = mFinance.get(position);
        final String selectedKey = selectedItem.getKey();

        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(AllFinance.this, "Task deleted", Toast.LENGTH_SHORT).show();
    }
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onShowItemClick(int position) {

        Finance clickedTeacher=mFinance.get(position);
        final String selectedKey = clickedTeacher.getKey();
        String[] teacherData={selectedKey,clickedTeacher.getName(),
                clickedTeacher.getEmail(),clickedTeacher.getAmount(),
                clickedTeacher.getBalance(),clickedTeacher.getDueDate(),
                clickedTeacher.getDate(),clickedTeacher.getDescription()
        };
        updateActivity(teacherData);
    }


}