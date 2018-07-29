package com.hzdongcheng.parcellocker.views;

import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.iv_net)
    ImageView ivNet;
    @BindView(R.id.iv_device)
    ImageView ivDevice;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_device_code)
    TextView tvDeviceCode;
    @BindView(R.id.tv_server_hot)
    TextView tvServerHot;
    @BindView(R.id.et_manager)
    EditText etManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        TimerUtils.getInstance().onTouch();
        return super.dispatchTouchEvent(ev);
    }

    MainViewmodel viewmodel;

    private void initView() {
        viewmodel = ViewModelProviders.of(this).get(MainViewmodel.class);
        getLifecycle().addObserver(viewmodel);
        ToastUtils.setBgResource(R.drawable.toast_background);

        viewmodel.mainModel.getCurrentFragment().observe(this, new Observer<WrapperFragment>() {
            @Override
            public void onChanged(@Nullable WrapperFragment aClass) {
                if (aClass != null && getFragmentManager().findFragmentByTag(aClass.getClass().getName()) == null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fl_container, aClass);
                    ft.commitAllowingStateLoss();
                }
            }
        });

        viewmodel.mainModel.getNetworkState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                aBoolean = aBoolean == null ? false : aBoolean;
                ivNet.setBackgroundResource(aBoolean ? R.drawable.ic_net_ok : R.drawable.ic_net_off);
            }
        });

        viewmodel.mainModel.getDeviceState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                aBoolean = aBoolean == null ? false : aBoolean;
                ivDevice.setBackgroundResource(aBoolean ? R.drawable.ic_device_ok : R.drawable.ic_device_off);
            }
        });

        viewmodel.mainModel.getVersion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvVersion.setText(s);
            }
        });
        viewmodel.mainModel.getDeviceCode().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvDeviceCode.setText(s);
            }
        });
        viewmodel.mainModel.getServerHot().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvServerHot.setText(s);
            }
        });
        etManager.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && etManager.isFocusable()) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP && etManager.getText().toString().equals("0000")) {
                        viewmodel.performManager();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.iv_logo)
    public void onViewClicked() {
        if (Objects.requireNonNull(viewmodel.mainModel.getCurrentFragment().getValue()) instanceof NavigateHomeFragment) {
            etManager.getText().clear();
            etManager.requestFocus();
        }
    }
}
