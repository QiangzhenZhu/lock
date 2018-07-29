package com.hzdongcheng.parcellocker.views.deliver;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliverOpenedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliverOpenedFragment extends WrapperFragment {
    DeliverViewmodel deliverViewmodel;

    public DeliverOpenedFragment() {

    }

    public static DeliverOpenedFragment newInstance() {
        DeliverOpenedFragment fragment = new DeliverOpenedFragment();
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
        View view = inflater.inflate(R.layout.fragment_deliver_opened, container, false);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                deliverViewmodel.model.getCurrentFragment().setValue(DeliverPackageFragment.class);
            }
        }, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
