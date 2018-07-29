package com.hzdongcheng.parcellocker.views.navigate;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigateHomeFragment extends WrapperFragment {
    @BindView(R.id.bt_delivery)
    Button btDelivery;
    @BindView(R.id.bt_pickup)
    Button btPickup;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.rl_breakdown)
    RelativeLayout rlBreakdown;
    Unbinder unbinder;

    public NavigateHomeFragment() {
        // Required empty public constructor
    }

    public static NavigateHomeFragment newInstance() {
        NavigateHomeFragment fragment = new NavigateHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    MainViewmodel viewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class);
        initView();
        return view;
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_rush, R.id.btn_finger, R.id.bt_delivery, R.id.bt_pickup})
    public void onViewClicked(View view) {
        if (StringUtils.isEmpty(DBSContext.ctrlParam.emergenciesPwd)) {
            viewmodel.mainModel.getCurrentFragment().postValue(RestoreRushFragment.newInstance());
        }

        switch (view.getId()) {
            case R.id.bt_delivery:
                viewmodel.performDelivery();
                break;
            case R.id.bt_pickup:
                viewmodel.performPickup();
                break;
            case R.id.btn_rush:
                viewmodel.performRush();
                break;
            case R.id.btn_finger:
                viewmodel.performFinger();
                break;
        }
    }
}
