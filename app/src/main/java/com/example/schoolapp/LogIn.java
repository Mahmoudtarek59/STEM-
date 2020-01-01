package com.example.schoolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity implements View.OnClickListener{

    private EditText usernameET,passwordET;
    private Button loginBtn;
    private TextView forgetPassTV;
    private CheckBox rememberMecheckBox;
    private Spinner userTypeSpinner;
    private SharedPreferences SharedPreferences;
    private static final String PREFS_NAME = "PrefsFile";
    private String remotly_Password;
    DatabaseReference myRef;
    private List<String> grade,classesGA,classesGB,classesGC;
    public static final String LOG_TAG = ListAdapter.class.getName();
    private String remotly_userType,remotly_Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        SharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        myRef=FirebaseDatabase.getInstance().getReference("High-school/Teachers");


        //grades
        grade=new ArrayList<String>();
        //classGA
        classesGA =new ArrayList<String>();
        classesGB =new ArrayList<String>();
        classesGC =new ArrayList<String>();

        remotly_userType="";
        remotly_Email="";
        remotly_Password="";
        //username and passeord
        usernameET = (EditText) findViewById(R.id.ET_username);
        passwordET = (EditText) findViewById(R.id.ET_password);
        loginBtn = (Button) findViewById(R.id.BT_login);
        forgetPassTV = (TextView) findViewById(R.id.TV_forgotpassword);

        //remember me
        rememberMecheckBox = (CheckBox) findViewById(R.id.checkbox_remember);

        loginBtn.setOnClickListener(this);
        forgetPassTV.setOnClickListener(this);

        //remember me
        getPreferencesData();


    }
    private void getPreferencesData() {
        SharedPreferences sp= getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        String usernameSP = null;
        String passwordSP = null;
        if(sp.contains("pref_name")){
            usernameSP = sp.getString("pref_name","not found.");
            usernameET.setText(usernameSP.toString());
        }
        if(sp.contains("pref_pass")){
            passwordSP = sp.getString("pref_pass","not found.");
            passwordET.setText(passwordSP.toString());
        }
        if(sp.contains("pref_check")){
            Boolean checkSP = sp.getBoolean("pref_check",false);
            rememberMecheckBox.setChecked(checkSP);
        }
         isValidData(usernameSP,passwordSP);
    }

    @Override
    public void onClick (View v){
        if (v.getId() == R.id.BT_login) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                isValidData(usernameET.getText().toString(),passwordET.getText().toString());
            }
        }else if(v.getId() == R.id.TV_forgotpassword){
            SpannableString content = new SpannableString(""+forgetPassTV.getText());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            forgetPassTV.setText(content);
            Toast.makeText(getApplicationContext(),"check your mail", Toast.LENGTH_SHORT).show();
            //TODO send mail to teatcher
        }
    }




    private void isValidData(final String username,final String pass){
        final String S_username = usernameET.getText().toString();
        final String S_pass = passwordET.getText().toString();
        if(username != null&&pass != null ){
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    grade.clear();
                    classesGA.clear();
                    classesGB.clear();
                    classesGC.clear();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if(dataSnapshot.child(username).exists()) {
                        remotly_userType=username;//check user name
                        if(dataSnapshot.child(username).child("Grade-A").exists()){
                            grade.add("Grade A");
                            for(DataSnapshot dt:dataSnapshot.child(username).child("Grade-A").getChildren()){
                                classesGA.add(dt.getKey());
                                Log.i(LOG_TAG,"______________________"+dt.getKey());
                              }
                        }if(dataSnapshot.child(username).child("Grade-B").exists()){
                            grade.add("Grade B");
                            for(DataSnapshot dt:dataSnapshot.child(username).child("Grade-B").getChildren()){
                                classesGB.add(dt.getKey());
                                Log.i(LOG_TAG,"______________________"+dt.getKey());
                            }
                        }if(dataSnapshot.child(username).child("Grade-C").exists()){
                            grade.add("Grade C");
                            for(DataSnapshot dt:dataSnapshot.child(username).child("Grade-C").getChildren()){
                                classesGC.add(dt.getKey());
                                Log.i(LOG_TAG,"______________________"+dt.getKey());
                            }
                        }
                        if(pass.equals(dataSnapshot.child(username).child("password").getValue(String.class))) {
                            remotly_Email=dataSnapshot.child(username).child("email").getValue(String.class);
                            //remember me
                            if(rememberMecheckBox.isChecked()){
                                Boolean boolIsChecked = rememberMecheckBox.isChecked();
                                SharedPreferences.Editor S_Editor = SharedPreferences.edit();
                                S_Editor.putString("pref_name", usernameET.getText().toString());
                                S_Editor.putString("pref_pass", passwordET.getText().toString());
                                S_Editor.putBoolean("pref_check", boolIsChecked);

                                S_Editor.apply();
                                Toast.makeText(getApplicationContext(),R.string.saved_data,Toast.LENGTH_SHORT).show();
                            }else{
                                SharedPreferences.edit().clear().apply();
                            }
                            // Validation Completed
                            passwordET.setCompoundDrawablesWithIntrinsicBounds( R.drawable.open, 0, 0, 0);
                            usernameET.getText().clear();
                            passwordET.getText().clear();
                            Intent intent=new Intent(LogIn.this, MainActivity.class);
                            intent.putStringArrayListExtra("grades",(ArrayList<String>) grade);
                            intent.putStringArrayListExtra("ClassesGA",(ArrayList<String>) classesGA);
                            intent.putStringArrayListExtra("ClassesGB",(ArrayList<String>) classesGB);
                            intent.putStringArrayListExtra("ClassesGC",(ArrayList<String>) classesGC);
                            intent.putExtra("remotly_Email",remotly_Email);
                            intent.putExtra("remotly_userType",remotly_userType);
                            startActivity(intent);
                            finish();
                        }else{
                            usernameET.setError("Invalid Data");
                        }
                    }else{
                        usernameET.setError("Invalid Data");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    usernameET.setError("Invalid Data");
                }
            });

        }
    }

}