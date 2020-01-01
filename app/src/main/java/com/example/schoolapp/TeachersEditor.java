package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TeachersEditor extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = TeachDataListAsyncTaskLoader.class.getName();

    private TextView subjectDateTV;
    private TextView subjectTimeTV;
    private EditText teacher_FullName_TV;
    private EditText teacher_Email_TV;
    private EditText teacher_Phone_TV;
    private EditText teacher_ID_TV;
    private EditText teacher_UserName_TV;
    private EditText teacher_Password_TV;
    private Spinner teacherGrade_SP;
    private Spinner teacherClass_SP;
    private Spinner teacherSubject_SP;
    private Button teacherSubmit_BT;

    private String TC_Name;
    private String TC_Email;
    private String TC_Phone;
    private String TC_ID;
    private String TC_UserName;
    private String TC_password;
    private String TC_Grade;
    private String TC_Class;
    private String TC_Subject;
    private String TC_date;
    private String TC_time;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_editor);

        setTitle("Teachers");
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("High-school");


        teacher_FullName_TV=(EditText)findViewById(R.id.ET_Teacher_Editor_name);
        teacher_Email_TV=(EditText)findViewById(R.id.ET_Teacher_Editor_email);
        teacher_Phone_TV=(EditText)findViewById(R.id.ET_Teacher_Editor_phone);
        teacher_ID_TV=(EditText)findViewById(R.id.ET_Teacher_Editor_id);
        teacher_UserName_TV=(EditText)findViewById(R.id.ET_Teacher_Editor_username);
        teacher_Password_TV=(EditText)findViewById(R.id.ET_Teacher_Editor_password);
        subjectDateTV=(TextView)findViewById(R.id.SubjectDate);
        subjectTimeTV=(TextView)findViewById(R.id.SubjectTime);

        teacherGrade_SP=(Spinner)findViewById(R.id.gradeSpinner);
        teacherClass_SP=(Spinner)findViewById(R.id.classSpinner);
        teacherSubject_SP=(Spinner)findViewById(R.id.subjectSpinner);

        teacherSubmit_BT=(Button)findViewById(R.id.teacher_submit);

        subjectDateTV.setOnClickListener(this);
        subjectTimeTV.setOnClickListener(this);
        teacherSubmit_BT.setOnClickListener(this);

        //add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==subjectDateTV) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_WEEK);

            DatePickerDialog dialog = new DatePickerDialog(
                    TeachersEditor.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            TC_date = dayOfMonth + "/" + month + "/" + year;
                            subjectDateTV.setText(TC_date);
                        }
                    },
                    year, month, day);
            dialog.show();
        }

        if(v==subjectTimeTV){
            Calendar cal=Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute= cal.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog=new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            TC_time=hourOfDay+":"+minute;
                            subjectTimeTV.setText(TC_time);
                        }
                    },hour,minute,false);
            timePickerDialog.show();
        }
        if(v==teacherSubmit_BT){
            getData();
            if(!TextUtils.isEmpty(TC_Name)&&
                    !TextUtils.isEmpty(TC_Email) &&
                    !TextUtils.isEmpty(TC_Phone) &&
                    !TextUtils.isEmpty(TC_ID) &&
                    !TextUtils.isEmpty(TC_UserName) &&
                    !TextUtils.isEmpty(TC_password) &&
                    teacherGrade_SP.getSelectedItem()!="Choose Grade" &&
                    teacherClass_SP.getSelectedItem() !="Choose Class" &&
                    teacherSubject_SP.getSelectedItem()!="Choose subject" &&
                    TC_date!=null &&
                    TC_time!=null) {

                showAlert();

//                Log.i(LOG_TAG, "name : " + TC_Name);
//                Log.i(LOG_TAG, "email : " + TC_Email);
//                Log.i(LOG_TAG, "phone : " + TC_Phone);
//                Log.i(LOG_TAG, "id : " + TC_ID);
//                Log.i(LOG_TAG, "UserName : " + TC_UserName);
//                Log.i(LOG_TAG, "password : " + TC_password);
//                Log.i(LOG_TAG, "Grade : " + TC_Grade);
//                Log.i(LOG_TAG, "Class : " + TC_Class);
//                Log.i(LOG_TAG, "Subject : " + TC_Subject);
//                Log.i(LOG_TAG, "date : " + TC_date);
//                Log.i(LOG_TAG, "Time : " + TC_time);

                //startActivity(new Intent(TeachersEditor.this,Teachers.class));
            }
        }
    }
    private void getData(){
        TC_Name = teacher_FullName_TV.getText().toString();
        TC_Email = teacher_Email_TV.getText().toString();
        TC_Phone = teacher_Phone_TV.getText().toString();
        TC_ID = teacher_ID_TV.getText().toString();
        TC_UserName = teacher_UserName_TV.getText().toString();
        TC_password = teacher_Password_TV.getText().toString();
        TC_Grade=(String)teacherGrade_SP.getSelectedItem();
        TC_Class=(String)teacherClass_SP.getSelectedItem();
        TC_Subject=(String)teacherSubject_SP.getSelectedItem();
    }
    private void showAlert(){
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you really want to add data?")
                .setIcon(android.R.drawable.ic_menu_save)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.child("Teachers").child(TC_UserName).child("email").setValue(TC_Email);
                        myRef.child("Teachers").child(TC_UserName).child("id").setValue(TC_ID);
                        myRef.child("Teachers").child(TC_UserName).child("name").setValue(TC_Name);
                        myRef.child("Teachers").child(TC_UserName).child("password").setValue(TC_password);
                        myRef.child("Teachers").child(TC_UserName).child("phone").setValue(TC_Phone);
                        myRef.child("Teachers").child(TC_UserName).child(TC_Grade).child(TC_Class).child(TC_Subject).child("date").setValue(TC_date);
                        myRef.child("Teachers").child(TC_UserName).child(TC_Grade).child(TC_Class).child(TC_Subject).child("time").setValue(TC_time);
                        myRef.child(TC_Grade).child(TC_Class).child("Subjects").child(TC_Subject).child("date").setValue(TC_date);
                        myRef.child(TC_Grade).child(TC_Class).child("Subjects").child(TC_Subject).child("time").setValue(TC_time);
                        myRef.child(TC_Grade).child(TC_Class).child("Subjects").child(TC_Subject).child("teacher").setValue(TC_Name);
                        Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().add(android.R.id.content,new Teachers()).commit();
                    }
                }).setNegativeButton(android.R.string.no,null).show();
    }
}
