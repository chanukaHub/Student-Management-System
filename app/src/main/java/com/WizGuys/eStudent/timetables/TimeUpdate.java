package com.WizGuys.eStudent.timetables;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.model.Teacher;
import com.WizGuys.eStudent.model.TimeTable;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class TimeUpdate extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseImageBtn;
    private Button uploadBtn;
    private EditText time8_10, time10_12, time12_2,
            time2_4,timedate,timeGrade;

    private ProgressBar uploadProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    //////////////////////////////

    private void initializeWidgets(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView ( R.layout.activity_time_tables );
        initializeWidgets();
        /////////////////////////////////////

        chooseImageBtn = findViewById(R.id.button_choose_image);
        uploadBtn = findViewById(R.id.timeSavebtn);

        //ET Text
        time8_10 = findViewById(R.id.time8_10);
        time10_12 = findViewById ( R.id.time10_12);
        time12_2 = findViewById ( R.id.time12_2 );
        time2_4 = findViewById(R.id.time2_4);
        timedate = findViewById(R.id.timedate);
        timeGrade = findViewById(R.id.timeGrade2);



                uploadProgressBar = findViewById(R.id.progress_bar);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("save_timeTable");
        ////////////////////
        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        String date=i.getExtras().getString("DATE");
        String grade=i.getExtras().getString("GRADE");
        String time8=i.getExtras().getString("Time8");
        String time10=i.getExtras().getString("Time10");
        String time12=i.getExtras().getString("Time12");
        String time2=i.getExtras().getString("Time2");


        timeGrade.setText(grade);
        timedate.setText(date);
        time8_10.setText(time8);
        time10_12.setText(time10);
        time2_4.setText(time2);
        time12_2.setText(time12);




        final String selectedKey  = id;




        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(TimeUpdate.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    updateUploadFile(selectedKey);
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void updateUploadFile(final String selectedKey) {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgressBar.setVisibility(View.VISIBLE);
                                    uploadProgressBar.setIndeterminate(false);
                                    uploadProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(TimeUpdate.this, "Teacher Update successful", Toast.LENGTH_LONG).show();

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            String downloadUrl = String.valueOf(urlTask.getResult());

                            TimeTable upload = new TimeTable(
                                    timeGrade.getText().toString().trim(),
                                    timedate.getText ().toString (),
                                    time8_10.getText().toString(),
                                    time10_12.getText().toString(),
                                    time12_2.getText().toString(),
                                    time2_4.getText().toString());

                            String uploadId = selectedKey;
                            mDatabaseRef.child(uploadId).setValue(upload);
                            uploadProgressBar.setVisibility(View.INVISIBLE);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(TimeUpdate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}