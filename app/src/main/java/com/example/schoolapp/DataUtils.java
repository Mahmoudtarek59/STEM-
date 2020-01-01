package com.example.schoolapp;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public final class DataUtils {

    static DatabaseReference myRef;
    public static final String LOG_TAG = DataUtils.class.getName();
    private DataUtils() {
    }
    public static List<ListWord> featchStudentData(String dataPath) {
        Log.d(LOG_TAG, "______________________________________________________1");
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //create fireBase
        // Write a message to the database


        List<ListWord> listWords=extractStudentData(dataPath);

        return listWords;

    }
    public static List<ListWord> extractStudentData(String path){
        Log.d(LOG_TAG, "______________________________________________________2");
        myRef = FirebaseDatabase.getInstance().getReference(path).child("Grade-A").child("Class-1").child("Students");
        final List<ListWord> studentList = new ArrayList<>();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email,full_name,id,phone;
                Long num_absent;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot dt:dataSnapshot.getChildren()) {
                    //ListWord value = dt.getValue(ListWord.class);
                    //studentList.add(value);
                    email=dt.child("email").getValue(String.class);
                    full_name=dt.child("full_name").getValue(String.class);
                    id=dt.child("id").getValue(String.class);
                    num_absent=dt.child("num_absent").getValue(Long.class);
                    phone=dt.child("phone").getValue(String.class);

                    studentList.add(new ListWord(email,full_name,id,num_absent,phone));
                    Log.d(LOG_TAG, "Value is: " + email);
                    Log.d(LOG_TAG, "Value is: " + full_name);
                    Log.d(LOG_TAG, "Value is: " + id);
                    Log.d(LOG_TAG, "Value is: " + num_absent);
                    Log.d(LOG_TAG, "Value is: " + phone);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(LOG_TAG, "Failed to read value.", error.toException());
            }
        });
        return studentList;
    }
}
