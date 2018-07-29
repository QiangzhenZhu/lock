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

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ManageHomeFragment extends WrapperFragment {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;

    Unbinder unbinder;
    @BindView(R.id.btn_deploy)
    Button btnDeploy;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.btn_detect)
    Button btnDetect;
    @BindView(R.id.btn_system_info)
    Button btnSystemInfo;

    public ManageHomeFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static ManageHomeFragment newInstance(String param1, String param2) {
        ManageHomeFragment fragment = new ManageHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_home, container, false);
        unbinder = ButterKnife.bind(this, view);
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
    public void onBtnGoBackClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
    }

    @OnClick(R.id.btn_deploy)
    public void onBtnDeployClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageDeployFragment.class);
    }

    @OnClick(R.id.btn_record)
    public void onBtnRecordClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageRecordsFragment.class);
    }

    @OnClick(R.id.btn_detect)
    public void onBtnDetectClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageBoxDetectFragment.class);
    }

    @OnClick(R.id.btn_system_info)
    public void onBtnSystemInfoClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageSystemInfoFragment.class);
    }
}
