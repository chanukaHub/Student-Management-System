//package com.WizGuys.eStudent.teachers;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.WizGuys.eStudent.R;
//
//public class MainActivity extends AppCompatActivity {
//    private Button openTeachersActivityBtn,openUploadActivityBtn;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate ( savedInstanceState );
//        setContentView ( R.layout.activity_main );
//        openTeachersActivityBtn = findViewById ( R.id.openTeachersActivityBtn );
//        openUploadActivityBtn = findViewById ( R.id.openUploadActivityBtn );
//        openTeachersActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, items.class);
//                startActivity(i);
//            }
//        });
//        openUploadActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, Upload.class);
//                startActivity(i);
//            }
//        });
//    }
//}