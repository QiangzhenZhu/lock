package com.hzdongcheng.parcellocker.views.manage;


import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.ManageModel;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.PickupViewmodel;
import com.hzdongcheng.parcellocker.views.pickup.PickupHolderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ManageHolderFragment extends WrapperFragment {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tv_time)
    TextView tvTime;
    Unbinder unbinder;

    public ManageHolderFragment() {
    }

    public static ManageHolderFragment newInstance() {
        ManageHolderFragment fragment = new ManageHolderFragment();
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
        manageViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ManageViewmodel.class);
        manageViewmodel.manageModel=new ManageModel();
        getLifecycle().addObserver(manageViewmodel);
        View view = inflater.inflate(R.layout.fragment_mange_holder, container, false);
        unbinder = ButterKnife.bind(this, view);
        TimerUtils.getInstance().addCountDownTimer(tvTime,getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        manageViewmodel.manageModel.getCurrentFragment().observe(this, new Observer<Class<? extends WrapperFragment>>() {
            @Override
            public void onChanged(@Nullable Class<? extends WrapperFragment> aClass) {
                if (aClass != null) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    try {
                        ft.replace(R.id.fl_container, aClass.newInstance());
                        ft.commitAllowingStateLoss();
                    } catch (java.lang.InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
