package com.hzdongcheng.parcellocker.views.manage;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.RichEditText;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageDeployFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageDeployFragment extends WrapperFragment {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    Unbinder unbinder;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.et_device_id)
    RichEditText etDeviceId;
    @BindView(R.id.et_device_name)
    RichEditText etDeviceName;
    @BindView(R.id.et_device_site)
    RichEditText etDeviceSite;
    @BindView(R.id.et_business_ip)
    RichEditText etBusinessIp;
    @BindView(R.id.et_business_port)
    RichEditText etBusinessPort;
    @BindView(R.id.et_monitoring_ip)
    RichEditText etMonitoringIp;
    @BindView(R.id.et_monitoring_port)
    RichEditText etMonitoringPort;
    @BindView(R.id.sh_normal_error)
    Switch shNormalError;
    @BindView(R.id.sh_repulse_onoff)
    Switch shRepulseOnoff;
    @BindView(R.id.sh_voice_onoff)
    Switch shVoiceOnoff;
    @BindView(R.id.sh_shake_onoff)
    Switch shShakeOnoff;
    @BindView(R.id.sh_goods_onoff)
    Switch shGoodsOnoff;
    @BindView(R.id.sh_power_onoff)
    Switch shPowerOnoff;

    public ManageDeployFragment() {
    }

    public static ManageDeployFragment newInstance(String param1, String param2) {
        ManageDeployFragment fragment = new ManageDeployFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_deploy, container, false);
        unbinder = ButterKnife.bind(this, view);
        shNormalError.setChecked(true);
        dataBinding();
        manageViewmodel.loadData();
        return view;
    }

    private void dataBinding() {
        manageViewmodel.manageModel.getTerminalNo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etDeviceId.setText(s);
                if (s != null || s != "") {
                    etDeviceId.setEnabled(false);
                    etDeviceId.setFocusable(false);
                }
            }
        });
        manageViewmodel.manageModel.getTerminalName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etDeviceName.setText(s);
            }
        });
        manageViewmodel.manageModel.getTrminalSite().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etDeviceSite.setText(s);
            }
        });
        manageViewmodel.manageModel.getSerHost().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etBusinessIp.setText(s);
            }
        });
        manageViewmodel.manageModel.getSerPort().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etBusinessPort.setText(s);
            }
        });
        manageViewmodel.manageModel.getMonHost().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etMonitoringIp.setText(s);
            }
        });
        manageViewmodel.manageModel.getMonPort().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                etMonitoringPort.setText(s);
            }
        });
        manageViewmodel.manageModel.getTerminalStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                shNormalError.setChecked(aBoolean);
            }
        });
        manageViewmodel.manageModel.getVoiceHint().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                shVoiceOnoff.setChecked(aBoolean);
            }
        });
        manageViewmodel.manageModel.getRepulseOpen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                shRepulseOnoff.setChecked(aBoolean);
            }
        });
        manageViewmodel.manageModel.getShakeDetect().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                shShakeOnoff.setChecked(aBoolean);
            }
        });
        manageViewmodel.manageModel.getGoodsDetect().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                shGoodsOnoff.setChecked(aBoolean);
            }
        });
        manageViewmodel.manageModel.getPowerDetect().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                shPowerOnoff.setChecked(aBoolean);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageHomeFragment.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        boolean bZXFlag = true;
        String sServerHost = etBusinessIp.getText().toString();
        String sMonServerHost = etMonitoringIp.getText().toString();
        if ((IsRightIP(sServerHost)) || (UrlCheck(sServerHost))) {
            if ((IsRightIP(sMonServerHost)) || (UrlCheck(sMonServerHost))) {
            } else {
                ToastUtils.makeText(getContext(), getResources().getString(R.string.error_manage_server_002), Toast.LENGTH_LONG).show();
                bZXFlag = false;
            }
        } else {
            ToastUtils.makeText(getContext(), getResources().getString(R.string.error_manage_server_002), Toast.LENGTH_LONG).show();
            bZXFlag = false;
        }

        String sServerPort = etBusinessPort.getText().toString();
        String sMonServerPort = etMonitoringPort.getText().toString();
        if (bZXFlag == true) {
            try {
                Integer.parseInt(sServerPort);
                Integer.parseInt(sMonServerPort);
            } catch (Exception e) {
                bZXFlag = false;
            }
        }
        if (bZXFlag == true) {
            if ((Integer.parseInt(sServerPort) < 0) || (Integer.parseInt(sServerPort) > 65535)) {
                bZXFlag = false;
                ToastUtils.makeText(getContext(), getResources().getString(R.string.error_manage_server_001), Toast.LENGTH_LONG).show();
                etBusinessPort.requestFocus();
            }
        }
        if (bZXFlag == true) {
            if ((Integer.parseInt(sMonServerPort) < 0) || (Integer.parseInt(sMonServerPort) > 65535)) {
                bZXFlag = false;
                ToastUtils.makeText(getContext(), getResources().getString(R.string.error_manage_server_001), Toast.LENGTH_LONG).show();
                etMonitoringPort.requestFocus();
            }
        }
        if (bZXFlag == true) {
            manageViewmodel.manageModel.getTerminalNo().setValue(etDeviceId.getText().toString());
            manageViewmodel.manageModel.getTerminalName().setValue(etDeviceName.getText().toString());
            manageViewmodel.manageModel.getTrminalSite().setValue(etDeviceSite.getText().toString());
            manageViewmodel.manageModel.getTerminalStatus().setValue(shNormalError.isChecked());
            manageViewmodel.manageModel.getVoiceHint().setValue(shVoiceOnoff.isChecked());
            manageViewmodel.manageModel.getRepulseOpen().setValue(shRepulseOnoff.isChecked());
            manageViewmodel.manageModel.getShakeDetect().setValue(shShakeOnoff.isChecked());
            manageViewmodel.manageModel.getGoodsDetect().setValue(shGoodsOnoff.isChecked());
            manageViewmodel.manageModel.getPowerDetect().setValue(shPowerOnoff.isChecked());
            manageViewmodel.manageModel.getSerHost().setValue(sServerHost);
            manageViewmodel.manageModel.getSerPort().setValue(sServerPort);
            manageViewmodel.manageModel.getMonHost().setValue(sMonServerHost);
            manageViewmodel.manageModel.getMonPort().setValue(sMonServerPort);

            manageViewmodel.saveload();
        }
    }

    //判断IP地址是否有效
    public static boolean IsRightIP(String ip) {
        Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        if (pattern.matcher(ip).matches()) {
            String[] ips = ip.split(".");
            if (ips.length == 4 || ips.length == 6) {
                if (Integer.parseInt(ips[0]) < 256 && Integer.parseInt(ips[1]) < 256 & Integer.parseInt(ips[2]) < 256 & Integer.parseInt(ips[3]) < 256)
                    return true;
                else
                    return false;
            } else
                return false;
        } else
            return false;
    }

    //判断输入的网址的有效性
    private boolean UrlCheck(String strUrl) {
        if (!strUrl.contains("http://") && !strUrl.contains("https://")) {
            strUrl = "http://" + strUrl;
        }
        return true;
    }
}
