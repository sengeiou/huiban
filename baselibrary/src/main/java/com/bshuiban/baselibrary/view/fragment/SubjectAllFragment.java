package com.bshuiban.baselibrary.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bshuiban.baselibrary.R;

/**
 * A simple {@link Fragment} subclass.
 * 总览
 */
public class SubjectAllFragment extends BaseFragment {

    public SubjectAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_all, container, false);
        view.findViewById(R.id.viewPager);
        view.findViewById(R.id.include);
        return view;
    }

}
