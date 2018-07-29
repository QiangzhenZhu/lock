package com.hzdongcheng.parcellocker.views.manage;


import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.SpinnerArrayAdapter;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DialogAddCabinetFragment extends DialogFragment {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.sp_package_statue)
    Spinner spPackageStatue;
    @BindView(R.id.rbtn_common_desk)
    RadioButton rbtnCommonDesk;
    @BindView(R.id.rbtn_fresh_desk)
    RadioButton rbtnFreshDesk;
    @BindView(R.id.rbtn_heat_desk)
    RadioButton rbtnHeatDesk;
    Unbinder unbinder;
    @BindView(R.id.tv_box_amount)
    TextView tvBoxAmount;
    @BindView(R.id.bt_manage_cancle)
    Button btManageCancle;
    @BindView(R.id.rg_desk_type)
    RadioGroup rgDeskType;
    @BindView(R.id.tv_desk_id)
    TextView tvDeskId;
    @BindView(R.id.tv_desk_type)
    TextView tvDeskType;
    @BindView(R.id.rbtn_laundry_desk)
    RadioButton rbtnLaundryDesk;
    @BindView(R.id.tv_desk_info)
    TextView tvDeskInfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        manageViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ManageViewmodel.class);
        View view = inflater.inflate(R.layout.fragment_dialog_add_cabinet, container, false);
        unbinder = ButterKnife.bind(this, view);
        rgDeskType.check(rbtnCommonDesk.getId());
        if (manageViewmodel.manageModel.boxesLayoutAdapter.getCabinetInfoSize()>0)
            tvDeskId.setText(manageViewmodel.manageModel.boxesLayoutAdapter.getCabinetInfo()
                .get(manageViewmodel.manageModel.boxesLayoutAdapter.getCabinetInfoSize()-1).deskId+"");
        else
            tvDeskId.setText("0");
        manageViewmodel.manageModel.getCabinetType().setValue(SysDict.DESk_TYPE_NARMAL);
        SpinnerArrayAdapter spinnerAdapter=new SpinnerArrayAdapter(getActivity().getApplication(),
                getResources().getStringArray(R.array.box_counts));
        spPackageStatue.setAdapter(spinnerAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rbtn_common_desk)
    public void onRbtnCommonDeskClicked() {
        manageViewmodel.manageModel.getCabinetType().setValue(SysDict.DESk_TYPE_NARMAL);
    }

    @OnClick(R.id.rbtn_fresh_desk)
    public void onRbtnFreshDeskClicked() {
        manageViewmodel.manageModel.getCabinetType().setValue(SysDict.DESK_TYPE_FRESH);
    }

    @OnClick(R.id.rbtn_heat_desk)
    public void onRbtnHeatDeskClicked() {
        manageViewmodel.manageModel.getCabinetType().setValue(SysDict.DESk_TYPE_HEAT);
    }
    @OnClick(R.id.rbtn_laundry_desk)
    public void onViewClicked() {
        manageViewmodel.manageModel.getCabinetType().setValue(SysDict.DESK_TYPE_LAUNDRY);
    }
    @OnClick(R.id.rg_desk_type)
    public void onRgDeskTypeClicked() {
        manageViewmodel.manageModel.getCabinetType().setValue(SysDict.DESK_TYPE_LAUNDRY);
    }

    @OnClick(R.id.bt_manage_cancle)
    public void onBtManageCancleClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.addSlaveCabinet(Integer.parseInt(spPackageStatue.getSelectedItem().toString()));
        this.dismiss();


    }


}
