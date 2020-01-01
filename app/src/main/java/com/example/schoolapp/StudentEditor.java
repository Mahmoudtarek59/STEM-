package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentEditor extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = TeachDataListAsyncTaskLoader.class.getName();

    private String gradeType;
    private String classType;


    private EditText student_FullName_TV;
    private EditText student_Email_TV;
    private EditText student_Phone_TV;
    private EditText student_ID_TV;
    private EditText student_numOFabsent_TV;
    private Button studentSubmit_BT;

    private String ST_Name;
    private String ST_Email;
    private String ST_Phone;
    private String ST_ID;
    private Long ST_Absent;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_editor);

        //remotly grade ,, remotly class
        gradeType = getIntent().getStringExtra("gradeType");
        classType = getIntent().getStringExtra("classType");

        setTitle(gradeType.substring(0, 1) + gradeType.substring(6, 7) + " (" + classType + ")");

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("High-school").child(gradeType).child(classType);

        student_FullName_TV = (EditText) findViewById(R.id.ET_Student_Editor_name);
        student_Email_TV = (EditText) findViewById(R.id.ET_Student_Editor_email);
        student_Phone_TV = (EditText) findViewById(R.id.ET_Student_Editor_phone);
        student_ID_TV = (EditText) findViewById(R.id.ET_Student_Editor_id);
        student_numOFabsent_TV = (EditText) findViewById(R.id.ET_Student_NM_Absent);

        studentSubmit_BT = (Button) findViewById(R.id.student_submit);

        studentSubmit_BT.setOnClickListener(this);
        //add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == studentSubmit_BT) {
            getData();
            if (!TextUtils.isEmpty(ST_Name) &&
                    !TextUtils.isEmpty(ST_Email) &&
                    !TextUtils.isEmpty(ST_ID) &&
                    !TextUtils.isEmpty(ST_Phone) &&
                    ST_Absent != null) {
                showAlert();
                Log.i(LOG_TAG, "name : " + ST_Name);
                Log.i(LOG_TAG, "email : " + ST_Email);
                Log.i(LOG_TAG, "phone : " + ST_Phone);
                Log.i(LOG_TAG, "id : " + ST_ID);
                Log.i(LOG_TAG, "Absent : " + ST_Absent);

            }
        }
    }

    private void getData() {
        ST_Name = student_FullName_TV.getText().toString();
        ST_Email = student_Email_TV.getText().toString();
        ST_ID = student_ID_TV.getText().toString();
        ST_Phone = student_Phone_TV.getText().toString();
        ST_Absent = Long.parseLong(student_numOFabsent_TV.getText().toString());
    }

    private void showAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you really want to add data?")
                .setIcon(android.R.drawable.ic_menu_save)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.child("Students").child(ST_Name).child("email").setValue(ST_Email);
                        myRef.child("Students").child(ST_Name).child("full_name").setValue(ST_Name);
                        myRef.child("Students").child(ST_Name).child("id").setValue(ST_ID);
                        myRef.child("Students").child(ST_Name).child("num_absent").setValue(ST_Absent);
                        myRef.child("Students").child(ST_Name).child("phone").setValue(ST_Phone);
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                        clearData();
                        Toast.makeText(getApplicationContext(), "Add another Student or back to home", Toast.LENGTH_SHORT).show();

                       // getSupportFragmentManager().beginTransaction().add(android.R.id.content, new Class()).commit();
                    }
                }).setNegativeButton(android.R.string.no, null).show();
    }

    public void clearData(){
        ST_Name = "";
        student_FullName_TV.setText("");
        student_FullName_TV.setHint("Full Name");
        ST_Email = "";
        student_Email_TV.setText("");
        student_Email_TV.setHint("E-Mail");
        ST_ID = "";
        student_ID_TV.setText("");
        student_ID_TV.setHint("Phone Number");
        ST_Phone ="";
        student_Phone_TV.setText("");
        student_Phone_TV.setHint("ID");
        ST_Absent =null;
        student_numOFabsent_TV.setText(null);
        student_numOFabsent_TV.setHint("Num OF Absent");

    }
}
