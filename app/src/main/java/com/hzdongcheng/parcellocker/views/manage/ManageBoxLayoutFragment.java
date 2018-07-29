package com.hzdongcheng.parcellocker.views.manage;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.BoxesLayoutAdapter;
import com.hzdongcheng.parcellocker.model.BoxModel;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;
import com.hzdongcheng.parcellocker.views.widget.RecyclerItemsClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 柜体布局配置
 */
public class ManageBoxLayoutFragment extends WrapperFragment implements RecyclerItemsClickListener {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    Unbinder unbinder;
    @BindView(R.id.btn_add_desk)
    Button btnAddDesk;
    @BindView(R.id.recycler_box_layout)
    RecyclerView recyclerBoxLayout;
    DialogSetupBoxFragment setupBoxFragment;
    DialogAddCabinetFragment addCabinetFragment;
    DialogSetupCabinetFragment setupCabinetFragment;
    @BindView(R.id.btn_desk_height)
    Button btnDeskHeight;
    @BindView(R.id.btn_delete_desk)
    Button btnDeleteDesk;

    public ManageBoxLayoutFragment() {
    }

    public static ManageBoxLayoutFragment newInstance(String param1, String param2) {
        ManageBoxLayoutFragment fragment = new ManageBoxLayoutFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_box_layout, container, false);
        unbinder = ButterKnife.bind(this, view);

        manageViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ManageViewmodel.class);
        manageViewmodel.manageModel.getBoxinfo().observe(this, new Observer<List<BoxModel>>() {
            @Override
            public void onChanged(@Nullable List<BoxModel> boxModels) {
                initView(boxModels == null ? new ArrayList<BoxModel>() :
                        boxModels);
            }
        });
        return view;
    }

    private void initView(final List<BoxModel> list) {
        manageViewmodel.manageModel.boxesLayoutAdapter = new BoxesLayoutAdapter(this, list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 16, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (list.size() == 0)
                    return 0;
                if (list.get(position).linear == 1) {
                    return gridLayoutManager.getSpanCount();
                } else {
                    return list.get(position).boxSpan;
                }
            }
        });
        recyclerBoxLayout.setLayoutManager(gridLayoutManager);
        recyclerBoxLayout.setAdapter(manageViewmodel.manageModel.boxesLayoutAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageBoxDetectFragment.class);
    }

    @OnClick(R.id.btn_desk_height)
    public void onBtnDeskHeightClicked() {
        if (setupCabinetFragment == null)
            setupCabinetFragment = new DialogSetupCabinetFragment();
        if (!setupCabinetFragment.isAdded())
            setupCabinetFragment.show(getFragmentManager(), "");
    }

    @OnClick(R.id.btn_delete_desk)
    public void onBtnDeleteDeskClicked() {
        manageViewmodel.manageModel.boxesLayoutAdapter.deleteSlaveCabinet();
    }

    @OnClick(R.id.btn_add_desk)
    public void onBtnAddDeskClicked() {
        addCabinetFragment = new DialogAddCabinetFragment();
        addCabinetFragment.show(getFragmentManager(), "");
    }

    @Override
    public void onClick(int position) {
        manageViewmodel.manageModel.boxId = manageViewmodel.manageModel.boxesLayoutAdapter.getCabinetInfo().get(position).boxId;
        manageViewmodel.manageModel.deskId = manageViewmodel.manageModel.boxesLayoutAdapter.getCabinetInfo().get(position).deskId;
        manageViewmodel.manageModel.boxfault = manageViewmodel.manageModel.boxesLayoutAdapter.getCabinetInfo().get(position).fault;
        //if (setupBoxFragment == null)
        setupBoxFragment = new DialogSetupBoxFragment();
        if (!setupBoxFragment.isAdded())
            setupBoxFragment.show(getFragmentManager(), "");
    }

}
