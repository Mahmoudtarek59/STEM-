package com.example.schoolapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListAdapter extends ArrayAdapter<ListWord> {

    String remotly_userType;
    Context context;
    CheckBox checkBox;
    public static final String LOG_TAG = ListAdapter.class.getName();
    public ListAdapter(Activity context, ArrayList<ListWord> ListWords,String remotly_userType) {
        super(context, 0, ListWords);
        this.context=context;
        this.remotly_userType=remotly_userType;
        Log.i(LOG_TAG,"_________________--------________ListAdapter");

    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        final ListWord listWord = getItem(position);
        TextView Student_name = (TextView) listItemView.findViewById(R.id.stuname);
        Log.i(LOG_TAG,"_________________--------________"+listWord.getFull_name());
        Student_name.setText(listWord.getFull_name());
        TextView Student_email=(TextView)listItemView.findViewById(R.id.stuemail);
        Student_email.setText(listWord.getEmail());

        //set number of absent
        final TextView num_absent=(TextView)listItemView.findViewById(R.id.num_absent);
        num_absent.setText(""+listWord.getNum_absent());
        checkBox=(CheckBox)listItemView.findViewById(R.id.checkbox_mark);
        checkBox.setChecked(listWord.isChecked());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                listWord.setIsChecked(isChecked);
            }

        });
        return listItemView;
    }


}
