package com.hzdongcheng.parcellocker.views.pickup;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.PickupListAdapter;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.PickupViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupListFragment extends WrapperFragment {

    PickupListAdapter pickupListAdapter;
    @BindView(R.id.pickup_list_view)
    ListView pickupListView;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    Unbinder unbinder;

    public PickupListFragment() {
        // Required empty public constructor
    }

    PickupViewmodel pickupViewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        pickupViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PickupViewmodel.class);
        pickupListAdapter = new PickupListAdapter(getActivity(), pickupViewmodel.pickupModel.getPickupList().getValue());
        pickupListView.setAdapter(pickupListAdapter);

        pickupViewmodel.pickupModel.getPickupList().observe(this, new Observer<List<OutParamPTObtainPickupList>>() {
            @Override
            public void onChanged(@Nullable List<OutParamPTObtainPickupList> outParamPTPickupQries) {
                pickupListAdapter.setPickupList(outParamPTPickupQries);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        pickupListAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
    }

    @OnClick(R.id.bt_submit)
    public void onBtSubmitClicked() {
        pickupViewmodel.performOpenCheckList();
    }

    @OnClick(R.id.bt_clear)
    public void onBtClearClicked() {
        for (OutParamPTObtainPickupList it : Objects.requireNonNull(pickupViewmodel.pickupModel.getPickupList().getValue())) {
            it.isChecked = false;
        }
        pickupListAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_check_all)
    public void onBtCheckAllClicked() {
        for (OutParamPTObtainPickupList it : Objects.requireNonNull(pickupViewmodel.pickupModel.getPickupList().getValue())) {
            it.isChecked = true;
        }
        pickupListAdapter.notifyDataSetChanged();
    }
}
