package com.example.schoolapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class TeacherListAdaptor extends ArrayAdapter<ListWord> {
    Context context;
    public static final String LOG_TAG = ListAdapter.class.getName();
    public TeacherListAdaptor(Activity context, ArrayList<ListWord> ListWords) {
        super(context, 0, ListWords);
        this.context=context;

    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.teacher_list_items, parent, false);
        }


        ListWord T_listWord = getItem(position);
        TextView Teacher_name = (TextView) listItemView.findViewById(R.id.teacherName);
        Teacher_name.setText(T_listWord.getTeacherFull_name());
        Log.i(LOG_TAG,"_________________--------________"+T_listWord.getTeacherFull_name());
        TextView Teacher_email=(TextView)listItemView.findViewById(R.id.teacherEmail);
        Teacher_email.setText(T_listWord.getTeacherEmail());
        Log.i(LOG_TAG,"_________________--------________"+T_listWord.getTeacherEmail());
        TextView Teacher_phone=(TextView)listItemView.findViewById(R.id.teacherPhone);
        Teacher_phone.setText(T_listWord.getTeacherPhone());
        Log.i(LOG_TAG,"_________________--------________"+T_listWord.getTeacherPhone());
        TextView Teacher_UserName=(TextView)listItemView.findViewById(R.id.teacherUserName);
        Teacher_UserName.setText("Username : "+T_listWord.getTeacherUsername());
        Log.i(LOG_TAG,"_________________--------________"+T_listWord.getTeacherUsername());
        TextView Teacher_Password=(TextView)listItemView.findViewById(R.id.teacherPassword);
        Teacher_Password.setText("Password : "+T_listWord.getTeacherPassword());
        Log.i(LOG_TAG,"_________________--------________"+T_listWord.getTeacherPassword());

        return listItemView;
    }


}
