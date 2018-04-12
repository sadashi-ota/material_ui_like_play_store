package com.sadashi.apps.ui.material.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sadashi.apps.ui.material.R;
import com.sadashi.apps.ui.material.views.adapter.SampleListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SampleFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        unbinder = ButterKnife.bind(this, view);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new SampleListAdapter(getContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
