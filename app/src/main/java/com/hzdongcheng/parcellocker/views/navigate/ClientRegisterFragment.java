package com.hzdongcheng.parcellocker.views.navigate;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientRegisterFragment extends WrapperFragment {


    @BindView(R.id.et_device_id)
    EditText etDeviceId;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    Unbinder unbinder;
    MainViewmodel viewmodel;

    public ClientRegisterFragment() {
    }

    public static ClientRegisterFragment newInstance() {
        ClientRegisterFragment fragment = new ClientRegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {
        if (etDeviceId.getText().length() <= 0) {
            ToastUtils.setBgResource(R.drawable.toast_background);
            ToastUtils.showLong("输入错误");
            return;
        }
        viewmodel.mainModel.getRegisterCode().setValue(etDeviceId.getText().toString());
        viewmodel.terminalRegister();
    }
}
