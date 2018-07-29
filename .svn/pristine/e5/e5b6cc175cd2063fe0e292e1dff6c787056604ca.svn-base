package com.hzdongcheng.parcellocker.views.deliver;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 指纹登录
 */
public class DeliverFingerFragment extends WrapperFragment {
    Unbinder unbinder;
    DeliverViewmodel deliverViewmodel;

    public DeliverFingerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deliver_finger, container, false);
        unbinder = ButterKnife.bind(this, view);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        deliverViewmodel.fingerLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onViewClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
    }
}
