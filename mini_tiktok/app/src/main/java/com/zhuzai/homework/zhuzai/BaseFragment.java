package com.mini_tiktok.homework.mini_tiktok;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected FragmentManager mFragmentManager = null;
    protected FragmentTransaction mFragmentTransaction = null;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        Log.i(TAG, "onAttach...");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onCreateView...");
//		View v = inflater.inflate(R.layout.messages_layout, container, false);

        return 	super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onActivityCreated...");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onStart...");
        super.onStart();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onResume...");
        super.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onPause...");
        super.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onStop...");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onDestroyView...");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onDestroy...");
        super.onDestroy();
    }

    public static BaseFragment newInstance(Context context, String tag){
        BaseFragment baseFragment =  null;

        return baseFragment;

    }

}
