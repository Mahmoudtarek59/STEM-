package com.example.schoolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout S_DrawerLayout;
    private ActionBarDrawerToggle S_Toggle;
    public static final String PREFS_NAME = "PrefsFile";
    public AlertDialog.Builder builderSingle;
    public ArrayList<String> grades,classesGA,classesGB,classesGC;
    public String gradeDefultType,classDefultType,remotly_userType,remotly_Email;
    NavigationView S_navigationView;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gradeDefultType="Grade-A";
        classDefultType="";
        //get Grades related to spacefic teacher
        grades=getIntent().getStringArrayListExtra("grades");
        classesGA=getIntent().getStringArrayListExtra("ClassesGA");
        classesGB=getIntent().getStringArrayListExtra("ClassesGB");
        classesGC=getIntent().getStringArrayListExtra("ClassesGC");
        remotly_userType=getIntent().getStringExtra("remotly_userType");
        remotly_Email=getIntent().getStringExtra("remotly_Email");




        Toast.makeText(this,"Welcome : "+remotly_userType,Toast.LENGTH_SHORT).show();

        //show alert dialog
        showAlert();

        //-----------------------------------side fragment--------------------------------//
        S_DrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        S_Toggle =new ActionBarDrawerToggle(this,S_DrawerLayout,R.string.open,R.string.close);
        S_DrawerLayout.addDrawerListener(S_Toggle);
        S_Toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        S_navigationView =(NavigationView)findViewById(R.id.nav_view);
        menu=S_navigationView.getMenu();
        if(remotly_userType.equals("admin")){
            menu.findItem(R.id.teachers).setVisible(true);
        }else{
            menu.findItem(R.id.teachers).setVisible(false);
        }
        S_navigationView.setNavigationItemSelectedListener(this);

        Bundle arguments=new Bundle();
        arguments.putString("gradeType",gradeDefultType);
        arguments.putString("classType",classDefultType);
        arguments.putString("remotly_userType",remotly_userType);
        Class myClass=new Class();
        myClass.setArguments(arguments);

            //FIRST SHOW AFTER LOGIN
 //          getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myClass).addToBackStack(null).commit();



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(S_Toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //naigation View action

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if( id == R.id.nav_signout){
            Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("pref_name");
            editor.remove("pref_pass");
            editor.remove("pref_check");
            editor.commit();
            finish();
            Intent intent = new Intent(getApplicationContext(),LogIn.class);
            startActivity(intent);
        }else if(id == R.id.class1){
            //send data to  class
            Bundle arguments=new Bundle();
            arguments.putString("gradeType",gradeDefultType);
            arguments.putString("classType","Class-1");
            arguments.putString("remotly_userType",remotly_userType);
            Class myClass=new Class();
            myClass.setArguments(arguments);
            //done
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,myClass).addToBackStack(null).commit();
        }else if(id == R.id.class2){
            Bundle arguments=new Bundle();
            arguments.putString("gradeType",gradeDefultType);
            arguments.putString("classType","Class-2");
            arguments.putString("remotly_userType",remotly_userType);
            Class myClass=new Class();
            myClass.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,myClass).addToBackStack(null).commit();
        }else if(id == R.id.class3){
            Bundle arguments=new Bundle();
            arguments.putString("gradeType",gradeDefultType);
            arguments.putString("classType","Class-3");
            arguments.putString("remotly_userType",remotly_userType);
            Class myClass=new Class();
            myClass.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,myClass).addToBackStack(null).commit();
        }else if(id == R.id.class4){
            Bundle arguments=new Bundle();
            arguments.putString("gradeType",gradeDefultType);
            arguments.putString("classType","Class-4");
            arguments.putString("remotly_userType",remotly_userType);
            Class myClass=new Class();
            myClass.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,myClass).addToBackStack(null).commit();
        }else if(id == R.id.class5){
            Bundle arguments=new Bundle();
            arguments.putString("gradeType",gradeDefultType);
            arguments.putString("classType","Class-5");
            arguments.putString("remotly_userType",remotly_userType);
            Class myClass=new Class();
            myClass.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,myClass).addToBackStack(null).commit();
        }else if(id == R.id.class6){
            Bundle arguments=new Bundle();
            arguments.putString("gradeType",gradeDefultType);
            arguments.putString("classType","Class-6");
            arguments.putString("remotly_userType",remotly_userType);
            Class myClass=new Class();
            myClass.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,myClass).addToBackStack(null).commit();
        }else if(id==R.id.teachers){

            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,new Teachers()).addToBackStack(null).commit();
        }
        else if(id==R.id.nav_grade){
            showAlert();
        }else if( id == R.id.nav_contact){
            Bundle arguments=new Bundle();
            arguments.putString("remotly_Email",remotly_Email);
            arguments.putString("remotly_userType",remotly_userType);
            Contact contact=new Contact();
            contact.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,contact).addToBackStack(null).commit();
        }

        S_DrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //alert
    public void showAlert(){
        builderSingle = new AlertDialog.Builder(MainActivity.this);
        //builderSingle.setIcon(R.drawable.logo);
        builderSingle.setTitle("Select Grade:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        for(String test:grades){
            arrayAdapter.add(test);
        }

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);

                AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        Toast.makeText(getApplicationContext(),""+strName,Toast.LENGTH_SHORT).show();
                        checkClasses(strName);
                        //move frame
                        Bundle arguments=new Bundle();
                        arguments.putString("gradeType",gradeDefultType);
                        arguments.putString("classType",classDefultType);
                        arguments.putString("remotly_userType",remotly_userType);
                        Class myClass=new Class();
                        myClass.setArguments(arguments);
                        classDefultType="";
                        //FIRST SHOW AFTER LOGIN
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,myClass).addToBackStack(null).commit();

                        dialog.dismiss();
                    }
                });
                builderInner.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        builderSingle.show().getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x68c8d6));

                    }
                });
                builderInner.show().getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x68c8d6));;
            }
        });
        builderSingle.show().getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0x000000, 0x68c8d6));

    }

    public void checkClasses(String gradeType){
        ArrayList<String> classType=new ArrayList<>();

        if(gradeType.equals("Grade A")){
            gradeDefultType="Grade-A";
            classType=(ArrayList<String>) classesGA.clone();
            Toast.makeText(getApplicationContext(),""+classType,Toast.LENGTH_SHORT).show();
        }else if(gradeType.equals("Grade B")){
            gradeDefultType="Grade-B";
            classType=(ArrayList<String>)classesGB.clone();
        }else if(gradeType.equals("Grade C")){
            gradeDefultType="Grade-C";
            classType=(ArrayList<String>)classesGC.clone();
            Toast.makeText(getApplicationContext(),""+classType,Toast.LENGTH_SHORT).show();
        }
        if(S_navigationView!=null){
            menu=S_navigationView.getMenu();
                if(classType.contains("Class-1")){
                    if(classDefultType.isEmpty()) {
                        classDefultType = "Class-1";
                    }
                    menu.findItem(R.id.class1).setVisible(true);
                }else{
                    menu.findItem(R.id.class1).setVisible(false);
                }
                if(classType.contains("Class-2")){
                    if(classDefultType.isEmpty()) {
                        classDefultType = "Class-2";
                    }
                    menu.findItem(R.id.class2).setVisible(true);
                }else{
                    menu.findItem(R.id.class2).setVisible(false);
                }
                if(classType.contains("Class-3")){
                    if(classDefultType.isEmpty()) {
                        classDefultType = "Class-3";
                    }
                    menu.findItem(R.id.class3).setVisible(true);
                }else{
                    menu.findItem(R.id.class3).setVisible(false);
                }
                if(classType.contains("Class-4")){
                    if(classDefultType.isEmpty()) {
                        classDefultType = "Class-4";
                    }
                    menu.findItem(R.id.class4).setVisible(true);
                }else{
                    menu.findItem(R.id.class4).setVisible(false);
                }
                if(classType.contains("Class-5")){
                    if(classDefultType.isEmpty()) {
                        classDefultType = "Class-5";
                    }
                    menu.findItem(R.id.class5).setVisible(true);
                }else{
                    menu.findItem(R.id.class5).setVisible(false);
                }
                if(classType.contains("Class-6")){
                    if(classDefultType.isEmpty()) {
                        classDefultType = "Class-6";
                    }
                    menu.findItem(R.id.class6).setVisible(true);
                }else{
                    menu.findItem(R.id.class6).setVisible(false);
                }
        }

    }
}
