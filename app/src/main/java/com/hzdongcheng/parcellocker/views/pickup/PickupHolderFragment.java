package com.hzdongcheng.parcellocker.views.pickup;

import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.PickupModel;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.PickupViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PickupHolderFragment extends WrapperFragment {
    private static final String ARG_DIRECT = "direct";
    private String direct;
    PickupViewmodel pickupViewmodel;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    Unbinder unbinder;

    public PickupHolderFragment() {
        // Required empty public constructor
    }

    public static PickupHolderFragment newInstance(String direct) {
        PickupHolderFragment fragment = new PickupHolderFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pickupViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PickupViewmodel.class);
        pickupViewmodel.pickupModel = new PickupModel();
        getLifecycle().addObserver(pickupViewmodel);
        View view = inflater.inflate(R.layout.fragment_pickup_holder, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        pickupViewmodel.pickupModel.getCurrentFragment().observe(this, new Observer<Class<? extends WrapperFragment>>() {
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

        if ("rush".equals(direct)) {
            pickupViewmodel.pickupModel.getCurrentFragment().postValue(PickupRushFragment.class);
        } else {
            pickupViewmodel.pickupModel.getCurrentFragment().postValue(PickupFingerFragment.class);
        }
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
