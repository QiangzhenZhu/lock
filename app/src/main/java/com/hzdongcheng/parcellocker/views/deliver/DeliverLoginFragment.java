package com.hzdongcheng.parcellocker.views.deliver;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.RichEditText;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 仓管员登录
 */
public class DeliverLoginFragment extends WrapperFragment {
    Unbinder unbinder;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    DeliverViewmodel deliverViewmodel;
    @BindView(R.id.et_user_name)
    RichEditText etUserName;
    @BindView(R.id.et_user_pwd)
    RichEditText etUserPwd;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    public DeliverLoginFragment() {
        // Required empty public constructor
    }

    public static DeliverLoginFragment newInstance(String param1, String param2) {
        DeliverLoginFragment fragment = new DeliverLoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_deliver_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBack() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {
        if (etUserName.getText().length() <= 0) {
            ToastUtils.showLong(R.string.hint_input_username);
            etUserName.requestFocus();
            return;
        }
        if (etUserPwd.getText().length() <= 0) {
            ToastUtils.showLong(R.string.hint_input_pwd);
            etUserPwd.requestFocus();
            return;
        }
        deliverViewmodel.model.getUserName().setValue(etUserName.getText().toString());
        deliverViewmodel.model.getPassword().setValue(etUserPwd.getText().toString());
        deliverViewmodel.login();

    }
}
