package com.geektech.weatherapplication.ui.test;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends BaseFragment {

    private TextView tvTitle;

    @Override
    protected int getViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
        setUpListeners();
    }


    private void setUpListeners() {
        tvTitle.setOnClickListener(v -> {
        });
    }

    private void setUpViews(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
    }
}
