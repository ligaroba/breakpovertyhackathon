package com.origitech.root.origitech.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origitech.root.origitech.R;

/**
 * Created by Origicheck on 12/10/2015.
 */
public class FragmentReportFake extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView= inflater.inflate(R.layout.fragment_reportfake,container,false);
        return RootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
