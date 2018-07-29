package com.hzdongcheng.parcellocker.views.manage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;


import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ManageSystemInfoFragment extends WrapperFragment {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    Unbinder unbinder;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.btn_system_log)
    Button btnSystemLog;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_system_versions)
    TextView tvSystemVersions;
    @BindView(R.id.tv_system_time)
    TextView tvSystemTime;

    public ManageSystemInfoFragment() {
    }

    public static ManageSystemInfoFragment newInstance(String param1, String param2) {
        ManageSystemInfoFragment fragment = new ManageSystemInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_system_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        tvSystemTime.setText(StringUtils.addQuote(date));
        tvSystemVersions.setText(DBSContext.currentVersion);
        return view;
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

    @OnClick(R.id.btn_go_back)
    public void onViewClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageHomeFragment.class);
    }

    @OnClick(R.id.btn_exit)
    public void onBtnExitClicked() {
        getActivity().finish();
    }

    @OnClick(R.id.btn_system_log)
    public void onBtnSystemLogClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageSystemInfoFragment.class);
    }
}
