package com.hzdongcheng.parcellocker.views.navigate;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 重置紧急密码
 */
public class RestoreRushFragment extends WrapperFragment {

    Unbinder unbinder;
    MainViewmodel viewmodel;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    public RestoreRushFragment() {
    }

    public static RestoreRushFragment newInstance() {
        RestoreRushFragment fragment = new RestoreRushFragment();
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
        View view = inflater.inflate(R.layout.fragment_deliver_restore, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        viewmodel.mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
    }

    @OnClick(R.id.bt_submit)
    public void onBtSubmitClicked() {
        viewmodel.restoreRush();
    }
}
