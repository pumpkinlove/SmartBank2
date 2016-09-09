package com.miaxis.smartbank.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.smartbank.R;

import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionFragment extends Fragment {

    public FunctionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_function, container, false);
        x.view().inject(this, v);
        return v;
    }

}
