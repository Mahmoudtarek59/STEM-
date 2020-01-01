package com.example.schoolapp;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
public class Teachers extends Fragment implements LoaderManager.LoaderCallbacks<Boolean>{

    private static final String LOG_TAG = Teachers.class.getName();


    private View rootView;
    Context mContext;

    private static ListView T_listItemView;
    private static TeacherListAdaptor teacherListAdaptor;
    private static ProgressBar T_ProgressBar;
    public TextView T_EmptyStateTextView;
    static ArrayList<ListWord> Twords;


    private static final int Data_LOADER = 1;
    private String path;
    static DatabaseReference myRef;


    public Teachers() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        teacherListAdaptor.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getActivity();
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_teachers, container, false);
        }



        //menu
        setHasOptionsMenu(true);


        path="High-school/Teachers";
        getActivity().setTitle("Teachers");
        T_EmptyStateTextView=(TextView)rootView.findViewById(R.id.teachersEmpty_view);
        T_ProgressBar = (ProgressBar) rootView.findViewById(R.id.teachersProgressPar);


        Twords = new ArrayList<ListWord>();

        teacherListAdaptor= new TeacherListAdaptor(getActivity(), Twords);
        T_listItemView = (ListView) rootView.findViewById(R.id.teachersList);
        T_listItemView.setAdapter(teacherListAdaptor);

        /*T_listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // ListWord listWord=teacherListAdaptor.getItem(position);
                Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();

            }
        });*/

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(Data_LOADER, null, this);
            T_listItemView.setEmptyView(T_EmptyStateTextView);

        }else{
            T_ProgressBar = (ProgressBar) rootView.findViewById(R.id.teachersProgressPar);
            T_ProgressBar.setVisibility(View.GONE);
            T_EmptyStateTextView.setText("no internet service");
        }


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.admin_editor, menu);
        menu.findItem(R.id.class_table_admin).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.admin_Add){
            Intent intent=new Intent(getActivity(),TeachersEditor.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "---------------------TESt onCreateLoader() _____________________");
        return new TeachDataListAsyncTaskLoader(getActivity(), path);
    }


    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        Log.i(LOG_TAG, "---------------------TESt onLoadFinished ____________________________________");
        T_ProgressBar = (ProgressBar) rootView.findViewById(R.id.teachersProgressPar);
        T_ProgressBar.setVisibility(View.GONE);
        //S_EmptyStateTextView.setText("no data found");
        //listAdapter.clear();
        if (data != null && data==true) {
            //listAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {
        Log.i(LOG_TAG, " TESt onReset()  ");
        teacherListAdaptor.clear();

    }
    /*****************************************************************************/
    public static List<ListWord> extractTeacherData(String path){
        myRef = FirebaseDatabase.getInstance().getReference(path);
        final List<ListWord> teacherList = new ArrayList<>();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Twords.clear();
                teacherList.clear();
                //teacherListAdaptor.clear();
                String email,full_name,id,phone,password,username;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot dt:dataSnapshot.getChildren()) {
                    //ListWord value = dt.getValue(ListWord.class);
                    //studentList.add(value);
                    //User name .getKey
                    username=dt.getKey();
                    email=dt.child("email").getValue(String.class);
                    full_name=dt.child("name").getValue(String.class);
                    id=dt.child("id").getValue(String.class);
                    phone=dt.child("phone").getValue(String.class);
                    password=dt.child("password").getValue(String.class);

                    teacherList.add(new ListWord(full_name,email,phone,id,password,username));

                }
                teacherListAdaptor.addAll(teacherList);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                //teacherListAdaptor.clear();
                // Failed to read value
                Log.w(LOG_TAG, "Failed to read value.", error.toException());
            }

        });
        return teacherList;
    }

}
