package com.WizGuys.eStudent.timetables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.finance.UploadFinance;
import com.WizGuys.eStudent.model.Finance;
import com.WizGuys.eStudent.model.TimeTable;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;

public class TimeTables  extends AppCompatActivity {

    private Button timeSavebtn;
    private EditText time8_10, time10_12, time12_2,
            time2_4,timedate,timeGrade;

    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_time_tables );

        timeSavebtn = findViewById(R.id.timeSavebtn);

        time8_10 = findViewById(R.id.time8_10);
        time10_12 = findViewById ( R.id.time10_12);
        time12_2 = findViewById ( R.id.time12_2 );
        time2_4 = findViewById(R.id.time2_4);
        timedate = findViewById(R.id.timedate);
        timeGrade = findViewById(R.id.timeGrade2);



        mDatabaseRef = FirebaseDatabase.getInstance().getReference("save_timeTable");

        timeSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(TimeTables.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }


    private void uploadFile() {

        TimeTable upload = new TimeTable(
                timedate.getText().toString(),
                timeGrade.getText().toString(),
                time8_10.getText().toString().trim(),
                time10_12.getText().toString(),
                time12_2.getText().toString(),
                time2_4.getText().toString()
        );
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload);

        Toast.makeText(TimeTables.this, "Upload Success", Toast.LENGTH_SHORT).show();

        timedate.setText("");
        timeGrade.setText("");
        time8_10.setText("");
        time10_12.setText("");
        time12_2.setText("");
        time2_4.setText("");

    }

}