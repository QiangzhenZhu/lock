package com.hzdongcheng.parcellocker.views.manage;


import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogSetupBoxFragment extends DialogFragment {

    ManageViewmodel manageViewmodel;

    @BindView(R.id.btn_small_box)
    Button btnSmallBox;
    @BindView(R.id.btn_mini_box)
    Button btnMiniBox;
    @BindView(R.id.btn_middle_box)
    Button btnMiddleBox;
    @BindView(R.id.btn_large_box)
    Button btnLargeBox;
    @BindView(R.id.btn_Super_box)
    Button btnSuperBox;
    Unbinder unbinder;
    @BindView(R.id.tv_desk_id)
    TextView tvDeskId;
    @BindView(R.id.tv_box_id)
    TextView tvBoxId;
    @BindView(R.id.btn_control_box)
    Button btnControlBox;
    @BindView(R.id.btn_adv_box)
    Button btnAdvBox;
    @BindView(R.id.tv_desk_info)
    TextView tvDeskInfo;
    @BindView(R.id.sh_normal_error)
    Switch shNormalError;

    public DialogSetupBoxFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        manageViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ManageViewmodel.class);
        View view = inflater.inflate(R.layout.fragment_dialog_setup_box, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvDeskId.setText(manageViewmodel.manageModel.deskId + "");
        tvBoxId.setText(manageViewmodel.manageModel.boxId + "");
        shNormalError.setChecked(manageViewmodel.manageModel.boxfault);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_small_box)
    public void onBtnSmallBoxClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxSpen(manageViewmodel.manageModel.boxId,
                manageViewmodel.manageModel.getBoxHeightQry().getValue().SmallHeight, SysDict.BOX_TYPE_SMALL);
        this.dismiss();

    }

    @OnClick(R.id.btn_mini_box)
    public void onBtnMiniBoxClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxSpen(manageViewmodel.manageModel.boxId,
                manageViewmodel.manageModel.getBoxHeightQry().getValue().MiniHeight, SysDict.BOX_TYPE_SUPERSMALL);
        this.dismiss();
    }

    @OnClick(R.id.btn_middle_box)
    public void onBtnMiddleBoxClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxSpen(manageViewmodel.manageModel.boxId,
                manageViewmodel.manageModel.getBoxHeightQry().getValue().MediumHeight, SysDict.BOX_TYPE_MEDIAL);
        this.dismiss();
    }

    @OnClick(R.id.btn_large_box)
    public void onBtnLargeBoxClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxSpen(manageViewmodel.manageModel.boxId,
                manageViewmodel.manageModel.getBoxHeightQry().getValue().LargeHeight, SysDict.BOX_TYPE_BIG);
        this.dismiss();
    }

    @OnClick(R.id.btn_Super_box)
    public void onBtnSuperBoxClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxSpen(manageViewmodel.manageModel.boxId,
                manageViewmodel.manageModel.getBoxHeightQry().getValue().SuperHeight, SysDict.BOX_TYPE_HUGE);
        this.dismiss();
    }

    @OnClick(R.id.btn_control_box)
    public void onBtnControlBoxClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxSpen(manageViewmodel.manageModel.boxId,
                manageViewmodel.manageModel.getBoxHeightQry().getValue().MasterHeight, SysDict.BOX_TYPE_MASTER);
        this.dismiss();
    }

    @OnClick(R.id.btn_adv_box)
    public void onBtnAdvBoxClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxSpen(manageViewmodel.manageModel.boxId,
                manageViewmodel.manageModel.getBoxHeightQry().getValue().AdvertisingHeight, SysDict.BOX_TYPE_ADVI);
        this.dismiss();
    }

    @OnClick(R.id.sh_normal_error)
    public void onViewClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.setBoxStatue(manageViewmodel.manageModel.boxId,shNormalError.isChecked());
        this.dismiss();
    }
}
