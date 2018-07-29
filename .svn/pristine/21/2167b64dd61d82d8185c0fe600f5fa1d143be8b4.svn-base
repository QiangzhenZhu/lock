package com.hzdongcheng.parcellocker.views.manage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.RichEditText;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ManageLoginFragment extends WrapperFragment {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.et_manager_id)
    RichEditText etManagerId;
    @BindView(R.id.et_manager_pwd)
    RichEditText etManagerPwd;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;

    public ManageLoginFragment() {
    }

    public static ManageLoginFragment newInstance(String param1, String param2) {
        ManageLoginFragment fragment = new ManageLoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        etManagerId.setText("165629");
        etManagerPwd.setText("999999");
        manageViewmodel.manageModel.getErrorTips().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvTips.setText(s);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
    }

    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
        if (etManagerId.getText().toString().isEmpty()) {
            tvTips.setText(getString(R.string.prompt_courier_user_name));
            return;
        }
        if (etManagerPwd.getText().toString().isEmpty()) {
            tvTips.setText(getString(R.string.prompt_courier_user_pwd));
            return;
        }
        manageViewmodel.manageModel.getUserName().setValue(etManagerId.getText().toString());
        manageViewmodel.manageModel.getPassword().setValue(etManagerPwd.getText().toString());
        manageViewmodel.login();
    }
}
