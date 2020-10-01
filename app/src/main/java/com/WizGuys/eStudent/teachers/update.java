//package com.WizGuys.eStudent.teachers;
//
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.WizGuys.eStudent.R;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//
//import lk.sliit.mad.testcrud.model.Teacher;
//
//public class update extends AppCompatActivity {
//
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private Button chooseImageBtn;
//    private Button uploadBtn;
//    private EditText nameEditText;
//    private EditText descriptionEditText;
//    private ImageView chosenImageView;
//    private ProgressBar uploadProgressBar;
//    private Uri mImageUri;
//    private StorageReference mStorageRef;
//    private DatabaseReference mDatabaseRef;
//    private StorageTask mUploadTask;
//    //////////////////////////////
//    private FirebaseStorage mStorage;
//
//    TextView nameDetailTextView,descriptionDetailTextView;
//    ImageView teacherDetailImageView;
//    private void initializeWidgets(){
//        nameDetailTextView= findViewById(R.id.nameEditText);
//        descriptionDetailTextView= findViewById(R.id.descriptionEditText);
//        teacherDetailImageView=findViewById(R.id.chosenImageView);
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload);
//        initializeWidgets();
//        /////////////////////////////////////
//
//        mStorage = FirebaseStorage.getInstance();
//
//        chooseImageBtn = findViewById(R.id.button_choose_image);
//        uploadBtn = findViewById(R.id.uploadBtn);
//        nameEditText = findViewById(R.id.nameEditText);
//        descriptionEditText = findViewById ( R.id.descriptionEditText );
//        chosenImageView = findViewById(R.id.chosenImageView);
//        uploadProgressBar = findViewById(R.id.progress_bar);
//        mStorageRef = FirebaseStorage.getInstance().getReference("teachers_uploads");
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("teachers_uploads");
//        ////////////////////
//        Intent i=this.getIntent();
//        String id=i.getExtras().getString("ID_KEY");
//        String name=i.getExtras().getString("NAME_KEY");
//        String description=i.getExtras().getString("DESCRIPTION_KEY");
//        String imageURL=i.getExtras().getString("IMAGE_KEY");
//
//
//        nameDetailTextView.setText(name);
//        descriptionDetailTextView.setText(description);
//        Picasso.with(this)
//                .load(imageURL)
//                .placeholder(R.drawable.amy)
//                .fit()
//                .centerCrop()
//                .into(teacherDetailImageView);
//
//
//        final String selectedKey  = id;
//        assert imageURL != null;
//        final StorageReference imageRef = mStorage.getReferenceFromUrl(imageURL);
//
//
//        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(update.this, "Item deleted", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                openFileChooser();
//            }
//        });
//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mUploadTask != null && mUploadTask.isInProgress()) {
//                    Toast.makeText(update.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
//                } else {
//                    updateUploadFile(selectedKey);
//                }
//            }
//        });
//    }
//
//    private String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//    private void updateUploadFile(final String selectedKey) {
//        if (mImageUri != null) {
//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                    + "." + getFileExtension(mImageUri));
//            uploadProgressBar.setVisibility(View.VISIBLE);
//            uploadProgressBar.setIndeterminate(true);
//            mUploadTask = fileReference.putFile(mImageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    uploadProgressBar.setVisibility(View.VISIBLE);
//                                    uploadProgressBar.setIndeterminate(false);
//                                    uploadProgressBar.setProgress(0);
//                                }
//                            }, 500);
//                            Toast.makeText(update.this, "Teacher Update successful", Toast.LENGTH_LONG).show();
//
//                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
//                            while (!urlTask.isSuccessful());
//                            String downloadUrl = String.valueOf(urlTask.getResult());
//
//                            Teacher upload = new Teacher(nameEditText.getText().toString().trim(),
//                                    downloadUrl,
//                                    descriptionEditText.getText().toString ());
//                            String uploadId = selectedKey;
//                            mDatabaseRef.child(uploadId).setValue(upload);
//                            uploadProgressBar.setVisibility(View.INVISIBLE);
//                            openImagesActivity ();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            uploadProgressBar.setVisibility(View.INVISIBLE);
//                            Toast.makeText(update.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            uploadProgressBar.setProgress((int) progress);
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void openImagesActivity(){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
//
//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null) {
//            mImageUri = data.getData();
//            Picasso.with(this).load(mImageUri).into(chosenImageView);
//        }
//    }
//}