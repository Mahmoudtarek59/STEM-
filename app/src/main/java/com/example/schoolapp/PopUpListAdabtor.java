package com.example.schoolapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PopUpListAdabtor extends ArrayAdapter<ListWord> {
    Context context;
    public static final String LOG_TAG = PopUpListAdabtor.class.getName();

    public PopUpListAdabtor(Activity context, ArrayList<ListWord> ListWords) {
        super(context, 0, ListWords);
        this.context=context;

    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.class_table_list_popup, parent, false);
        }


        ListWord T_listWord = getItem(position);

        TextView ClassTableTeacher_name = (TextView) listItemView.findViewById(R.id.classTableTeatcherName);
        ClassTableTeacher_name.setText(T_listWord.getTableClassSubjectTeacherName());

        TextView ClassTableTeacher_subject = (TextView) listItemView.findViewById(R.id.classTableclassTableSubjectTitle);
        ClassTableTeacher_subject.setText(T_listWord.getTableClassSubjectTeacherSubject());

        TextView ClassTableTeacher_subject_Date = (TextView) listItemView.findViewById(R.id.classTableclassTableSubjectDate);
        ClassTableTeacher_subject_Date.setText(T_listWord.getTableClassSubjectTeacherDate());

        TextView ClassTableTeacher_subject_Time = (TextView) listItemView.findViewById(R.id.classTableclassTableSubjectTime);
        ClassTableTeacher_subject_Time.setText(T_listWord.getTableClassSubjectTeacheTime());


        return listItemView;
    }
}
