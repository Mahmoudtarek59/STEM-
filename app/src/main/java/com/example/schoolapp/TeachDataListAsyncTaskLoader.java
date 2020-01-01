package com.example.schoolapp;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

public class TeachDataListAsyncTaskLoader extends AsyncTaskLoader<Boolean> {

    private static final String LOG_TAG = TeachDataListAsyncTaskLoader.class.getName();
    private String S_path;
    public TeachDataListAsyncTaskLoader(Context context, String path){
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
        Teachers.extractTeacherData(S_path);

        return true;
    }
}