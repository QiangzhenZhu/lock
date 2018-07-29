package com.hzdongcheng.parcellocker.views.pickup;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.RichEditText;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.PickupViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 紧急密码取件
 */
public class PickupRushFragment extends WrapperFragment {

    @BindView(R.id.et_rush_pwd)
    RichEditText etRushPwd;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    Unbinder unbinder;
    PickupViewmodel pickupViewmodel;

    public PickupRushFragment() {
    }

    public static PickupRushFragment newInstance() {
        PickupRushFragment fragment = new PickupRushFragment();
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
        View view = inflater.inflate(R.layout.fragment_pickup_rush, container, false);
        unbinder = ButterKnife.bind(this, view);
        pickupViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PickupViewmodel.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_submit)
    public void onBtSubmitClicked() {
        if (etRushPwd.getText().length() <= 0) {
            ToastUtils.showShort(R.string.hint_pickup_rush_pwd);
        }
        if (Objects.equals(DBSContext.ctrlParam.emergenciesPwd,etRushPwd.getText().toString()))
            pickupViewmodel.urgentOpenBox();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
    }
}
