package com.example.schoolapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;


import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Class extends Fragment implements LoaderManager.LoaderCallbacks<Boolean>{

    private static final String LOG_TAG = Class.class.getName();

    private View rootView;
    Context mContext;
    private static ListView listItemView;

    private static ProgressBar S_ProgressBar;
    public  TextView S_EmptyStateTextView;
    private static final int Data_LOADER_ID = 1;

    private static ListAdapter listAdapter;

    private String path;

     static DatabaseReference myRef;
     private static String gradeType,classType,remotly_userType;
      static ArrayList<ListWord> words;
      String studentAbsentName;
      Long studentAbsentNumber;
     //public String customPath;
//     CheckBox checkBox;
//     ArrayList<String> studentAbsents;

    //popup
    private Dialog popupDialog;
    static ArrayList<ListWord> tableWords;
    static PopUpListAdabtor tableListAdapter;
    static private List<String> studentsEmail;

    @Override
    public void onStart() {
        super.onStart();
        listAdapter.clear();
    }

    public Class() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            mContext = getActivity();
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_class, container, false);
        }


        //menu
        setHasOptionsMenu(true);

        //get data path from the last activity
        Bundle bundle=this.getArguments();
        gradeType=bundle.getString("gradeType","Grade-A");
        classType=bundle.getString("classType","Class-1");
        remotly_userType=bundle.getString("remotly_userType");
        path="High-school/"+gradeType+"/"+classType+"";

        studentsEmail=new ArrayList<>();

//      studentAbsents=new ArrayList<>();

        //change title bar
        getActivity().setTitle(gradeType.substring(0,1)+gradeType.substring(6,7)+" ("+ classType +")");

        myRef = FirebaseDatabase.getInstance().getReference(path).child("Students");

        S_EmptyStateTextView=(TextView)rootView.findViewById(R.id.empty_view);
        S_ProgressBar = (ProgressBar) rootView.findViewById(R.id.progressPar);
//       checkBox =(CheckBox)rootView.findViewById(R.id.checkbox_mark);

        words = new ArrayList<ListWord>();
        //words.add(new ListWord("mahmoud tarek"));
        //words.add(new ListWord("mahmoud tarek"));
        //words.add(new ListWord("mahmoud tarek"));
        listAdapter = new ListAdapter(getActivity(), words,remotly_userType);
        listItemView = (ListView) rootView.findViewById(R.id.list);
        listItemView.setAdapter(listAdapter);
//      studentAbsents=(ArrayList<String>) listAdapter.studentCheckAbsent.clone();

        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListWord listWord=listAdapter.getItem(position);
                showStudentInfoPopUP(listWord.getFull_name(),
                        listWord.getEmail(),
                        listWord.getPhone(),
                        listWord.getId(),
                        listWord.getNum_absent());

            }
        });
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(Data_LOADER_ID, null, this);
            listItemView.setEmptyView(S_EmptyStateTextView);

        }else{
            S_ProgressBar = (ProgressBar) rootView.findViewById(R.id.progressPar);
            S_ProgressBar.setVisibility(View.GONE);
            S_EmptyStateTextView.setText("no internet service");
        }
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if(classType!="") {
            if (!remotly_userType.equals("admin")) {
                inflater.inflate(R.menu.menu_editor, menu);
                super.onCreateOptionsMenu(menu, inflater);
            } else {
                inflater.inflate(R.menu.admin_editor, menu);
                super.onCreateOptionsMenu(menu, inflater);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.edit_menu_save){
            new AlertDialog.Builder(mContext)
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to save Absent of student for today?")
                    .setIcon(android.R.drawable.ic_menu_send)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           Toast.makeText(mContext,"wait",Toast.LENGTH_SHORT).show();
                            for(int i = 0;i<words.size();i++){
                                if(words.get(i).isChecked()){
                                    words.get(i).setIsChecked(false);
                                    studentAbsentName=words.get(i).getFull_name();
                                    studentAbsentNumber=words.get(i).getNum_absent()+1;
                                    myRef.child(words.get(i).getFull_name()).child("num_absent").setValue(studentAbsentNumber);
                                }
                            }
                            Toast.makeText(mContext,"done",Toast.LENGTH_SHORT).show();
//                            Bundle arguments=new Bundle();
//                            arguments.putString("gradeType",gradeType);
//                            arguments.putString("classType",classType);
//                            arguments.putString("remotly_userType",remotly_userType);
//                            Class myClass=new Class();
//                            myClass.setArguments(arguments);
//                            getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content,myClass).commit();
                        }
                    }).setNegativeButton(android.R.string.no,null).show();



            return true;
        }if(item.getItemId()==R.id.admin_Add){
            Intent intent=new Intent(getActivity(),StudentEditor.class);
            intent.putExtra("gradeType",gradeType);
            intent.putExtra("classType",classType);
            startActivity(intent);
            return true;
        }if(item.getItemId()==R.id.class_table || item.getItemId()==R.id.class_table_admin){
            showPopUp();
            return true;
        }if(item.getItemId()==R.id.send_email){
            boolean checked=false;
            for(int i = 0;i<words.size();i++){
                if(words.get(i).isChecked()){
                    checked=true;
                }
            }
            if(checked) {
                studentsEmail.clear();
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).isChecked()) {
                        studentsEmail.add(words.get(i).getEmail());
                    }
                }
            }
            sendEmailPopUP();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void sendEmailPopUP(){
        popupDialog=new Dialog(mContext);
        popupDialog.setContentView(R.layout.pop_up_email);
        TextView closePopUp=(TextView)popupDialog.findViewById(R.id.closePopUpMenuEmail);
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });
        Button send_Email=(Button)popupDialog.findViewById(R.id.send_email_message);
        send_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView TV_Email_message=popupDialog.findViewById(R.id.ET_teacher_message);
                String[] StuEmail=new String[studentsEmail.size()];
                StuEmail=studentsEmail.toArray(StuEmail);
                Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,StuEmail);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "STEM:/ " + remotly_userType);
                emailIntent.putExtra(Intent.EXTRA_TEXT, TV_Email_message.getText().toString());
                if ( emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    TV_Email_message.setText("");
                    startActivity(emailIntent);
                    popupDialog.dismiss();
                    Toast.makeText(mContext,"Done",Toast.LENGTH_SHORT).show();
                }
            }
        });
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDialog.show();

    }

    public void showPopUp(){
        popupDialog=new Dialog(mContext);
        popupDialog.setContentView(R.layout.custompopup);
        TextView closePopUp=(TextView)popupDialog.findViewById(R.id.closePopUpMenu);
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });
        TextView classNamePop=(TextView)popupDialog.findViewById(R.id.className);
        classNamePop.setText(classType);
        tableWords= new ArrayList<ListWord>();
        tableListAdapter = new PopUpListAdabtor(getActivity(), tableWords);
        ListView PopUplistItemView = (ListView) popupDialog.findViewById(R.id.tableList);
        PopUplistItemView.setAdapter(tableListAdapter);
        extractTableSubjectData(path);
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDialog.show();
    }

    public void showStudentInfoPopUP(final String StuInfoName,String StuInfoEmail,String StuInfoPhone,String StuInfoID,Long StuInfoAbsence){
        popupDialog=new Dialog(mContext);
        popupDialog.setContentView(R.layout.pop_list_item_class);
        TextView closePopUp=(TextView)popupDialog.findViewById(R.id.closeStuPopUpMenu);
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });
        TextView popUpGrade=(TextView)popupDialog.findViewById(R.id.informationStudentGrade);
        popUpGrade.setText(gradeType);
        TextView popUpClass=(TextView)popupDialog.findViewById(R.id.informationStudentClass);
        popUpClass.setText(classType);
        TextView popUpStuName=(TextView)popupDialog.findViewById(R.id.informationStudintName);
        popUpStuName.setText(StuInfoName);

        TextView popUpStuEmail=(TextView)popupDialog.findViewById(R.id.informationStudentEmail);
        popUpStuEmail.setText(StuInfoEmail);

        TextView popUpStuPhone=(TextView)popupDialog.findViewById(R.id.informationStudentPhone);
        popUpStuPhone.setText(StuInfoPhone);

        TextView popUpStuID=(TextView)popupDialog.findViewById(R.id.informationStudentID);
        popUpStuID.setText("ID : "+StuInfoID);

        TextView popUpStuAbsence=(TextView)popupDialog.findViewById(R.id.informationStudentNumOfAbsence);
        popUpStuAbsence.setText("Absence : "+StuInfoAbsence);

        Button removeStuBtn=(Button)popupDialog.findViewById(R.id.informationStudentDelete);
        if (remotly_userType.equals("admin")) {
            removeStuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirmation")
                            .setMessage("Do you really want to remove this student?")
                            .setIcon(android.R.drawable.ic_menu_delete)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        myRef = FirebaseDatabase.getInstance().getReference(path).child("Students");
                                        myRef.child(StuInfoName).removeValue();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    popupDialog.dismiss();
                                    Toast.makeText(mContext, "done", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton(android.R.string.no, null).show();
                }
            });
        }else{
            removeStuBtn.setVisibility(View.GONE);
        }

        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDialog.show();

    }


    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "---------------------TESt onCreateLoader() ");
        return new DataListAsyncTaskLoader(getActivity(), path);
    }


    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        Log.i(LOG_TAG, "---------------------TESt onLoadFinished class");
        S_ProgressBar = (ProgressBar) rootView.findViewById(R.id.progressPar);
        S_ProgressBar.setVisibility(View.GONE);
        //S_EmptyStateTextView.setText("no data found");
        //listAdapter.clear();
        if (data != null && data==true) {
            //listAdapter.addAll(data);
       }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {
        Log.i(LOG_TAG, " TESt onReset()  ");
        listAdapter.clear();

    }
    /*****************************************************************************/
    public static void extractStudentData(String path){
        myRef = FirebaseDatabase.getInstance().getReference(path).child("Students");
        final List<ListWord> studentList = new ArrayList<>();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                words.clear();
                studentList.clear();
                studentsEmail.clear();
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

                    studentsEmail.add(email);
                    studentList.add(new ListWord(email,full_name,id,num_absent,phone));

                }
                listAdapter.addAll(studentList);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value
                Log.w(LOG_TAG, "Failed to read value.", error.toException());
            }

        });

    }
    public static void extractTableSubjectData(String path){
        myRef = FirebaseDatabase.getInstance().getReference(path).child("Subjects");
        final List<ListWord> SubjectList = new ArrayList<>();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tableWords.clear();
                SubjectList.clear();

                String subjectName,teacherName,subjectDate,subjectTime;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot dt:dataSnapshot.getChildren()) {
                    //ListWord value = dt.getValue(ListWord.class);
                    //studentList.add(value);
                    subjectName=dt.getKey();
                    teacherName=dt.child("teacher").getValue(String.class);
                    subjectDate=dt.child("date").getValue(String.class);
                    subjectTime=dt.child("time").getValue(String.class);

                    SubjectList.add(new ListWord(teacherName,subjectName,subjectDate,subjectTime));

                }
                tableListAdapter.addAll(SubjectList);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value
                Log.w(LOG_TAG, "Failed to read value.", error.toException());
            }

        });

    }

}
