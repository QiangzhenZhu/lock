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
import android.widget.EditText;
import android.widget.Spinner;

import com.hzdongcheng.bll.basic.dto.OutParamTBBoxHeightQry;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.SpinnerArrayAdapter;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogSetupCabinetFragment extends DialogFragment {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.sp_small_size)
    Spinner spSmallSize;
    @BindView(R.id.sp_middle_size)
    Spinner spMiddleSize;
    @BindView(R.id.sp_large_size)
    Spinner spLargeSize;
    @BindView(R.id.sp_mini_size)
    Spinner spMiniSize;
    @BindView(R.id.sp_super_size)
    Spinner spSuperSize;
    @BindView(R.id.sp_control_size)
    Spinner spControlSize;
    @BindView(R.id.sp_adv_size)
    Spinner spAdvSize;
    @BindView(R.id.bt_manage_confirm)
    Button btManageConfirm;
    Unbinder unbinder;
    @BindView(R.id.et_desk_size)
    EditText etDeskSize;
    public DialogSetupCabinetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        manageViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ManageViewmodel.class);
        View view = inflater.inflate(R.layout.fragment_dialog_setup_cabinet, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        manageViewmodel.getBoxesSpan();
        etDeskSize.setText(manageViewmodel.manageModel.getBoxHeightQry().getValue().TerminalHeight+"");
        spSmallSize.setSelection(manageViewmodel.manageModel.getBoxHeightQry().getValue().SmallHeight - 1, true);
        spMiddleSize.setSelection(manageViewmodel.manageModel.getBoxHeightQry().getValue().MediumHeight - 1, true);
        spLargeSize.setSelection(manageViewmodel.manageModel.getBoxHeightQry().getValue().LargeHeight - 1, true);
        spMiniSize.setSelection(manageViewmodel.manageModel.getBoxHeightQry().getValue().MiniHeight - 1, true);
        spSuperSize.setSelection(manageViewmodel.manageModel.getBoxHeightQry().getValue().SuperHeight - 1, true);
        spControlSize.setSelection(manageViewmodel.manageModel.getBoxHeightQry().getValue().MasterHeight - 1, true);
        spAdvSize.setSelection(manageViewmodel.manageModel.getBoxHeightQry().getValue().AdvertisingHeight - 1, true);

        SpinnerArrayAdapter spinnerAdapter=new SpinnerArrayAdapter(getActivity().getApplication(),
                getResources().getStringArray(R.array.box_size));
        spSmallSize.setAdapter(spinnerAdapter);
        spMiddleSize.setAdapter(spinnerAdapter);
        spLargeSize.setAdapter(spinnerAdapter);
        spMiniSize.setAdapter(spinnerAdapter);
        spSuperSize.setAdapter(spinnerAdapter);
        spControlSize.setAdapter(spinnerAdapter);
        spAdvSize.setAdapter(spinnerAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_manage_confirm)
    public void onViewClicked() {
        OutParamTBBoxHeightQry boxHeightQry = new OutParamTBBoxHeightQry();
        boxHeightQry.TerminalHeight=Integer.parseInt(etDeskSize.getText().toString());
        boxHeightQry.SmallHeight = Integer.parseInt(spSmallSize.getSelectedItem().toString());
        boxHeightQry.MediumHeight = Integer.parseInt(spMiddleSize.getSelectedItem().toString());
        boxHeightQry.LargeHeight = Integer.parseInt(spLargeSize.getSelectedItem().toString());
        boxHeightQry.MiniHeight = Integer.parseInt(spMiniSize.getSelectedItem().toString());
        boxHeightQry.SuperHeight = Integer.parseInt(spSuperSize.getSelectedItem().toString());
        boxHeightQry.MasterHeight = Integer.parseInt(spControlSize.getSelectedItem().toString());
        boxHeightQry.AdvertisingHeight = Integer.parseInt(spAdvSize.getSelectedItem().toString());

        manageViewmodel.manageModel.getBoxHeightQry().setValue(boxHeightQry);
        manageViewmodel.setBoxesSpan();
        this.dismiss();
    }

}
