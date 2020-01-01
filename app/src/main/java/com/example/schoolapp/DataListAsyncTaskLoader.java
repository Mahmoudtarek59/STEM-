package com.example.schoolapp;

import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

public class DataListAsyncTaskLoader extends AsyncTaskLoader<Boolean> {

    private static final String LOG_TAG = DataListAsyncTaskLoader.class.getName();
    private String S_path;
    public DataListAsyncTaskLoader(Context context, String path){
        super(context);
        S_path=path;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"_________________TESt onStartLoading");
        forceLoad();
    }
    @Override
    public Boolean loadInBackground() {
        Log.i(LOG_TAG,"_________________TESt loadInBackground");
        if (S_path == null) {
            return false;
        }
        Class.extractStudentData(S_path);
        return true;
    }
}
