package com.hzdongcheng.parcellocker.views.deliver;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliverOpenedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliverOpeningFragment extends WrapperFragment {
    Unbinder unbinder;
    DeliverViewmodel deliverViewmodel;
    @BindView(R.id.tv_open_box_tips)
    TextView tvOpenBoxTips;

    public DeliverOpeningFragment() {

    }

    public static DeliverOpeningFragment newInstance(String param1, String param2) {
        DeliverOpeningFragment fragment = new DeliverOpeningFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deliver_opening, container, false);
        unbinder = ButterKnife.bind(this, view);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        SpannableStringBuilder builder = new SpannableStringBuilder("请将钥匙放入" + deliverViewmodel.model.boxName + "号柜内，并关闭柜门");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        builder.setSpan(span, 6, 6 + deliverViewmodel.model.boxName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvOpenBoxTips.setText(builder);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TimerUtils.getInstance().cancelCountDownTimer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_retry)
    public void onBtRetryClicked() {
        deliverViewmodel.retryOpenBox(DeliverOpeningFragment.this);
    }


    @OnClick(R.id.bt_fault)
    public void onBtFaultClicked() {
        deliverViewmodel.setBoxFault();
    }
}
