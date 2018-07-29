package com.hzdongcheng.parcellocker.views.deliver;

import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.DeliverModel;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DeliverHolderFragment extends WrapperFragment {
    private static final String ARG_DIRECT = "direct";
    private String direct;
    @BindView(R.id.tv_time)
    TextView tvTime;
    Unbinder unbinder;

    public DeliverHolderFragment() {
    }

    public static DeliverHolderFragment newInstance(String direct) {
        DeliverHolderFragment fragment = new DeliverHolderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DIRECT, direct);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            direct = getArguments().getString(ARG_DIRECT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ("login".equals(direct)) {
            deliverViewmodel.model.getCurrentFragment().postValue(DeliverLoginFragment.class);
        } else {
            deliverViewmodel.model.getCurrentFragment().postValue(DeliverFingerFragment.class);
        }
    }

    DeliverViewmodel deliverViewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deliver_holder, container, false);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        deliverViewmodel.model = new DeliverModel();
        getLifecycle().addObserver(deliverViewmodel);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        deliverViewmodel.model.getCurrentFragment().observe(this, new Observer<Class<? extends WrapperFragment>>() {
            @Override
            public void onChanged(@Nullable Class<? extends WrapperFragment> aClass) {
                if (aClass != null) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    try {
                        ft.replace(R.id.fl_container, aClass.newInstance());
                        ft.commitAllowingStateLoss();
                    } catch (java.lang.InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
