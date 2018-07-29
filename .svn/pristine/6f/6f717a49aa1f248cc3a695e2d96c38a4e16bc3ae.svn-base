package com.hzdongcheng.parcellocker.views.deliver;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryRecordQry;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.RecyclerRecordAdapter;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeliverRecordFragment extends WrapperFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.recycler_view_record)
    RecyclerView recyclerViewRecord;
    Unbinder unbinder;
    DeliverViewmodel deliverViewmodel;
    private RecyclerView.LayoutManager mLayoutManager;

    public DeliverRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deliver_record, container, false);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        unbinder = ButterKnife.bind(this, view);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewRecord.setLayoutManager(mLayoutManager);
        deliverViewmodel.model.getOutParamPTDeliveryRecordQrys().observe(this, new Observer<List<OutParamPTDeliveryRecordQry>>() {
            @Override
            public void onChanged(@Nullable List<OutParamPTDeliveryRecordQry> outParamPTDeliveryRecordQries) {
                RecyclerRecordAdapter mAdapter = new RecyclerRecordAdapter(container.getContext(), outParamPTDeliveryRecordQries);
                recyclerViewRecord.setAdapter(mAdapter);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onViewClicked() {
        deliverViewmodel.model.getCurrentFragment().postValue(DeliverHomeFragment.class);
    }
}
