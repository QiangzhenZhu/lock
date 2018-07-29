package com.hzdongcheng.parcellocker.views.pickup;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.PickupModel;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.PickupViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupFingerFragment extends WrapperFragment {


    Unbinder unbinder;

    public PickupFingerFragment() {
        // Required empty public constructor
    }

    PickupViewmodel pickupViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pickup_finger, container, false);
        pickupViewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PickupViewmodel.class);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        pickupViewModel.fingerLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_go_back, R.id.bt_pickup_by_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_go_back:
                ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
                break;
            case R.id.bt_pickup_by_code:
                pickupViewModel.cancelFingerCollect();
                pickupViewModel.pickupModel.getCurrentFragment().setValue(PickupCodeFragment.class);
                break;
        }
    }
}
