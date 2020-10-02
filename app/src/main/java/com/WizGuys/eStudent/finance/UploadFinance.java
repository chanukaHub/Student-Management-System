package com.WizGuys.eStudent.finance;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.model.Teacher;
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


public class UploadFinance extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button uploadBtnFin;
    private EditText finName, finEmail,finAmountReceived,
            finBalance,finBalanceToPay,
            date,finDescription;
    private ImageView chosenImageView;
    private ProgressBar uploadProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_payment );

        uploadBtnFin = findViewById(R.id.uploadBtnFin);


        finName = findViewById(R.id.finName);
        finEmail = findViewById ( R.id.finEmail );
        finAmountReceived = findViewById ( R.id.finAmountReceived );
        finBalance = findViewById ( R.id.finBalance );
        finBalanceToPay = findViewById ( R.id.finBalanceToPay );
        date = findViewById ( R.id.findate );
        finDescription = findViewById ( R.id.finDescription );
        uploadProgressBar = findViewById(R.id.progress_bar);
        mStorageRef = FirebaseStorage.getInstance().getReference("finance_uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("finance_uploads");

        uploadBtnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadFinance.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(chosenImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
/*
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
                            Toast.makeText(UploadFinance.this, "Teacher Upload successful", Toast.LENGTH_LONG).show();

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            String downloadUrl = String.valueOf(urlTask.getResult());

                            Teacher upload = new Teacher(finName.getText().toString().trim(),
                                    downloadUrl,
                                    techDescription.getText ().toString (),
                                    techAddress.getText().toString(),
                                    techContact.getText().toString(),
                                    techEmail.getText().toString(),
                                    techQualification.getText().toString(),
                                    techSalary.getText().toString()
                                    );
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            openImagesActivity ();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UploadFinance.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        }*/
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, TeachersDashboard.class);
        startActivity(intent);
    }
}