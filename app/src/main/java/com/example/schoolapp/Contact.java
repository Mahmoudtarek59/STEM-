package com.example.schoolapp;


import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contact extends Fragment implements View.OnClickListener{


    private View rootView;
    private Context mContext;
    private EditText ET_teacherFullName,ET_teacherEmail,ET_teacherMessage;
    private Button BT_submitEmail;
    private ImageButton IM_BT_call;

    private String remotly_Email,remotly_userType;
    public Contact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        if (rootView == null) {
            mContext = getActivity();
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        }

        //change title bar
        getActivity().setTitle(R.string.contact);


        Bundle bundle=this.getArguments();
        remotly_Email=bundle.getString("remotly_Email");
        remotly_userType=bundle.getString("remotly_userType","Teacher");



        ET_teacherFullName=(EditText) rootView.findViewById(R.id.ET_teacher_name);
        ET_teacherEmail=(EditText) rootView.findViewById(R.id.ET_teacher_mail);
        ET_teacherMessage=(EditText) rootView.findViewById(R.id.ET_teacher_message);
        BT_submitEmail =(Button) rootView.findViewById(R.id.BT_submit);
        IM_BT_call =(ImageButton) rootView.findViewById(R.id.Bt_call);

        ET_teacherEmail.setText(ET_teacherEmail.getText().toString());
        ET_teacherFullName.setText(remotly_userType);
        //action
        BT_submitEmail.setOnClickListener(this);
        IM_BT_call.setOnClickListener(this);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.BT_submit){
            if(!TextUtils.isEmpty(ET_teacherMessage.getText().toString())){
            //TODO import admin mail from firebase , Teacher name
                String[] email={"samymostafa172@gmail.com"};

                Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,email);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "STEM:/ " + remotly_userType);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "hello : \n" + ET_teacherMessage.getText().toString());


//            emailIntent.putExtra(Intent.EXTRA_EMAIL,"mahmoudtarek9396@gmail.com");//here teacher email, from email
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "STEM:/ " + remotly_userType);
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "hello : \n" + ET_teacherMessage.getText().toString());
            if ( emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                ET_teacherMessage.setText("");
                startActivity(emailIntent);
                Toast.makeText(mContext, "we will contact you soon...", Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(mContext, "please enter your message", Toast.LENGTH_SHORT).show();
            }

        }else if(v.getId() == R.id.Bt_call){
            //TODO import admin number frm  firebase
            Intent phoneCallIntent= new Intent(Intent.ACTION_DIAL);
            phoneCallIntent.setData(Uri.parse("tel:01155282915"));
            if (phoneCallIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(phoneCallIntent);
            }
        }
    }
}
