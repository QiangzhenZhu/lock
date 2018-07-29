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

import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.BoxesDetectAdapter;
import com.hzdongcheng.parcellocker.adapter.BoxesLayoutAdapter;
import com.hzdongcheng.parcellocker.model.BoxModel;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;
import com.hzdongcheng.parcellocker.views.widget.RecyclerItemsClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ManageBoxDetectFragment extends WrapperFragment implements RecyclerItemsClickListener {

    ManageViewmodel manageViewmodel;
    @BindView(R.id.recycler_box_layout)
    RecyclerView recyclerBoxLayout;
    Unbinder unbinder;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.btn_full_open)
    Button btnFullOpen;
    @BindView(R.id.btn_box_layout)
    Button btnBoxLayout;
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future = null;

    public ManageBoxDetectFragment() {
    }

    public static ManageBoxDetectFragment newInstance(String param1, String param2) {
        ManageBoxDetectFragment fragment = new ManageBoxDetectFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_box_detect, container, false);
        unbinder = ButterKnife.bind(this, view);
        manageViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ManageViewmodel.class);
        manageViewmodel.getBoxesSpan();
        initView();
        future = service.scheduleWithFixedDelay(futureRunnable, 2, 3, TimeUnit.SECONDS);
        return view;
    }

    private void initView() {
        final List<BoxModel> list = manageViewmodel.queryBoxInfo();
        manageViewmodel.manageModel.boxesDetectAdapter = new BoxesDetectAdapter(this, list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), manageViewmodel.manageModel.getBoxHeightQry().getValue().TerminalHeight, GridLayoutManager.HORIZONTAL, false);
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
        recyclerBoxLayout.setAdapter(manageViewmodel.manageModel.boxesDetectAdapter);

    }

    @Override
    public void onClick(int position) {
        if (manageViewmodel.manageModel.getBoxinfo().getValue().get(position).door != -1)
            manageViewmodel.openOneBox(manageViewmodel.manageModel.getBoxinfo().getValue().get(position).boxName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (future != null)
            future.cancel(true);
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onViewClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageHomeFragment.class);
    }

    @OnClick(R.id.btn_full_open)
    public void onBtnFullOpenClicked() {
        manageViewmodel.openfullBox();
    }

    @OnClick(R.id.btn_box_layout)
    public void onBtnBoxLayoutClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageBoxLayoutFragment.class);
    }

    Runnable futureRunnable = new Runnable() {
        @Override
        public void run() {
            manageViewmodel.manageModel.boxesDetectAdapter.changeBoxStatus(manageViewmodel.queryBoxInfo());
        }
    };
}
