package com.hzdongcheng.parcellocker.views.deliver;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 录入指纹
 */
public class DeliverInputFingerFragment extends WrapperFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    DeliverViewmodel deliverViewmodel;

    public DeliverInputFingerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deliver_finger, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTitle.setText(R.string.prompt_input_finger);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onViewClicked() {
        deliverViewmodel.cancelFingerCollect();
        deliverViewmodel.model.getCurrentFragment().setValue(DeliverHomeFragment.class);
    }
}
