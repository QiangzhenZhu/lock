package com.hzdongcheng.parcellocker.views.pickup;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.PickupOpenedAdapter;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.PickupViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;
import com.hzdongcheng.parcellocker.views.widget.InformationFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 取件开箱界面-列表
 */
public class PickupOpenedFragment extends WrapperFragment {

    @BindView(R.id.btn_go_back)
    Button btToHome;
    @BindView(R.id.bt_submit)
    Button btPickupContinue;
    Unbinder unbinder;
    @BindView(R.id.pickup_list_view)
    ListView pickupListView;

    public PickupOpenedFragment() {
        // Required empty public constructor
    }


    public static PickupOpenedFragment newInstance(String param1, String param2) {
        PickupOpenedFragment fragment = new PickupOpenedFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    PickupViewmodel pickupViewmodel;
    DeliverViewmodel deliverViewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup_opened, container, false);
        unbinder = ButterKnife.bind(this, view);
        pickupViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PickupViewmodel.class);
        initView();
        return view;
    }

    PickupOpenedAdapter listAdapter;

    private void initView() {
        listAdapter = new PickupOpenedAdapter(getActivity(), pickupViewmodel.pickupModel.getCheckedList().getValue());
        pickupListView.setAdapter(listAdapter);
        TimerUtils.getInstance().cancelCountDownTimer();

        pickupViewmodel.pickupModel.getCheckedList().observe(this, new Observer<List<OutParamPTObtainPickupList>>() {
            @Override
            public void onChanged(@Nullable List<OutParamPTObtainPickupList> outParamPTObtainPickupLists) {
                listAdapter.setPickupLists(outParamPTObtainPickupLists);
                listAdapter.notifyDataSetChanged();
            }
        });

        pickupViewmodel.pickupModel.updateListView.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                listAdapter.notifyDataSetChanged();
            }
        });

        listAdapter.setListener(new PickupOpenedAdapter.OnAdapterInteractionListener() {
            @Override
            public void onAdapterInteraction(View view) {
                int i = (int) view.getTag();
                final OutParamPTObtainPickupList out = pickupViewmodel.pickupModel.getCheckedList().getValue().get(i);
                switch (view.getId()) {
                    case R.id.bt_retry:
                        if (out.retryCount > 3) {
                            SpannableStringBuilder builder = new SpannableStringBuilder("若"+ out.boxName+"号柜门仍未打开\n" +
                                    "请钉钉联系钥匙管理员" );
                            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#FFD260"));
                            builder.setSpan(span, 1, 1 + out.boxName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            final InformationFragment informationFragment = InformationFragment.newInstance("已重试三次？",builder);
                            informationFragment.setOnFragmentInteractionListener(new InformationFragment.OnFragmentInteractionListener() {
                                @Override
                                public void onFragmentInteraction(View view) {
                                    if (view.getId() == R.id.bt_positive) {
                                        // TODO: 2018/7/27
                                        pickupViewmodel.takeOutFailed(out);
                                    }
                                    informationFragment.dismiss();
                                }
                            });
                            informationFragment.show(getChildFragmentManager(),PickupOpenedFragment.class.getSimpleName());
                        } else {
                            pickupViewmodel.retryOpenBox(out.boxName);
                            out.retryCount++;
                        }
                        break;
                    case R.id.bt_take_out:
                        pickupViewmodel.hadTakeOut(out);
                        out.status = 1;
                        listAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        pickupViewmodel.pickupModel.getCheckedList().observe(this, new Observer<List<OutParamPTObtainPickupList>>() {
            @Override
            public void onChanged(@Nullable List<OutParamPTObtainPickupList> outParamPTObtainPickupLists) {
                listAdapter.setPickupLists(outParamPTObtainPickupLists);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtToHomeClicked() {
        if (pickupViewmodel.canContinue()) {
            ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().postValue(NavigateHomeFragment.newInstance());
        }
    }

    @OnClick(R.id.bt_submit)
    public void onBtPickupContinueClicked() {
        if (pickupViewmodel.canContinue()) {
            pickupViewmodel.pickupContinue();
        }
    }
}
