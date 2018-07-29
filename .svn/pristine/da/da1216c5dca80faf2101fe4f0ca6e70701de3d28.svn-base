package com.hzdongcheng.parcellocker.views.navigate;


import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigateLoaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigateLoaderFragment extends WrapperFragment {

    @BindView(R.id.tv_tips)
    TextView tvTips;
    Unbinder unbinder;

    public NavigateLoaderFragment() {
        // Required empty public constructor
    }

    public static NavigateLoaderFragment newInstance() {
        NavigateLoaderFragment fragment = new NavigateLoaderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_loader, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainViewmodel viewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class);
        viewmodel.mainModel.getProcessTips().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvTips.setText(s);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
