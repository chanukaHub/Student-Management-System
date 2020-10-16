package com.WizGuys.eStudent.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.model.Student;
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

public class StudentUpdate extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseStudentImageBtn;
    private Button addStudentBtn;
    private EditText nameEditText, emailEditText,addressEditText,contactEditText,indexEditText;
    private ImageView chosenStudentImageView;
    private ProgressBar uploadProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private FirebaseStorage mStorage;

    TextView nameDetailTextView,indexDetailTextView;
    ImageView studentImageView;
    private void initializeWidgets(){
        nameDetailTextView= findViewById(R.id.updateStudentNameEditText);
        indexDetailTextView= findViewById(R.id.updateStudentIndexEditText);
        studentImageView=findViewById(R.id.chosenUpdateStudentImageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update);
        initializeWidgets();

        mStorage = FirebaseStorage.getInstance();

        chooseStudentImageBtn = findViewById(R.id.button_choose_update_student_image);
        addStudentBtn = findViewById(R.id.updateStudentBtn);

        nameEditText = findViewById(R.id.updateStudentNameEditText);
        emailEditText = findViewById ( R.id.updateStudentEmailEditText );
        addressEditText = findViewById ( R.id.updateStudentAddressEditText );
        contactEditText = findViewById ( R.id.updateStudentContactEditText );
        indexEditText = findViewById ( R.id.updateStudentIndexEditText );


        chosenStudentImageView = findViewById(R.id.chosenUpdateStudentImageView);
        uploadProgressBar = findViewById(R.id.student_update_progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("Student");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Student");
        ////////////////////
        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        String index=i.getExtras().getString("INDEX_KEY");
        String name=i.getExtras().getString("NAME_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");
        String address=i.getExtras().getString("ADDRESS_KEY");
        String contact=i.getExtras().getString("CONTACT_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");

        nameDetailTextView.setText(name);
        emailEditText.setText(email);
        contactEditText.setText(contact);
        addressEditText.setText(address);
        indexDetailTextView.setText(index);
        Picasso.with(this)
                .load(imageURL)
                .placeholder(R.drawable.amy)
                .fit()
                .centerCrop()
                .into(studentImageView);


        final String selectedKey  = id;
        assert imageURL != null;
        final StorageReference imageRef = mStorage.getReferenceFromUrl(imageURL);


        chooseStudentImageBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(StudentUpdate.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                openFileChooser();
            }
        });
        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(StudentUpdate.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(StudentUpdate.this, "Teacher Update successful", Toast.LENGTH_LONG).show();

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            String downloadUrl = String.valueOf(urlTask.getResult());

                            Student upload = new Student(nameEditText.getText().toString().trim(),
                                    indexEditText.getText().toString().trim(),
                                    addressEditText.getText().toString().trim(),
                                    contactEditText.getText().toString().trim(),
                                    emailEditText.getText().toString().trim(),
                                    downloadUrl);
                            String uploadId = selectedKey;
                            mDatabaseRef.child(indexEditText.getText().toString().trim()).setValue(upload);
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            openImagesActivity ();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(StudentUpdate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void openImagesActivity(){
        Intent intent = new Intent(this, StudentItems.class);
        startActivity(intent);
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
            Picasso.with(this).load(mImageUri).into(chosenStudentImageView);
        }
    }
}