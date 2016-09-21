package com.miaxis.smartbank.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.smartbank.R;
import com.miaxis.smartbank.activity.doing.NewDoingActivity;

import org.xutils.view.annotation.Event;
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

    @Event(R.id.ll_new_doing)
    private void newDoing(View view) {
        startActivity(new Intent(getActivity(), NewDoingActivity.class));
    }

}
