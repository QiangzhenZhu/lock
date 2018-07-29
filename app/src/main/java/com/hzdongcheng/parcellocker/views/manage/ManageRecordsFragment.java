package com.hzdongcheng.parcellocker.views.manage;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryRecordQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryRecordQry;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.adapter.ManageRecordAdapter;
import com.hzdongcheng.parcellocker.adapter.SpinnerArrayAdapter;
import com.hzdongcheng.parcellocker.utils.RichEditText;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 快递柜使用记录查询
 */
public class ManageRecordsFragment extends WrapperFragment {
    ManageViewmodel manageViewmodel;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.btn_start_date)
    Button btnStartDate;
    @BindView(R.id.btn_end_date)
    Button btnEndDate;
    @BindView(R.id.et_phono_id)
    RichEditText etPhonoId;
    @BindView(R.id.sp_package_statue)
    Spinner spPackageStatue;
    @BindView(R.id.et_package_id)
    RichEditText etPackageId;
    @BindView(R.id.et_courier_id)
    RichEditText etCourierId;
    @BindView(R.id.lv_record)
    ListView lvRecord;
    Unbinder unbinder;
    int mYear;
    int mMonth;
    int mDay;
    @BindView(R.id.btn_query)
    Button btnQuery;

    public ManageRecordsFragment() {
    }

    public static ManageRecordsFragment newInstance(String param1, String param2) {
        ManageRecordsFragment fragment = new ManageRecordsFragment();
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
        manageViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(ManageViewmodel.class);
        View view = inflater.inflate(R.layout.fragment_manage_records, container, false);
        unbinder = ButterKnife.bind(this, view);
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        manageViewmodel.manageModel.getOutDeliveryRecordQry().observe(this, new Observer<List<OutParamPTDeliveryRecordQry>>() {
            @Override
            public void onChanged(@Nullable List<OutParamPTDeliveryRecordQry> outParamPTDeliveryRecordQries) {
                ManageRecordAdapter adapter = new ManageRecordAdapter(getActivity(), outParamPTDeliveryRecordQries);
                lvRecord.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        SpinnerArrayAdapter spinnerAdapter = new SpinnerArrayAdapter(getActivity().getApplication(),
                getResources().getStringArray(R.array.package_status));
        spPackageStatue.setAdapter(spinnerAdapter);
        spPackageStatue.setSelection(4, true);
        return view;
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

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        manageViewmodel.manageModel.getCurrentFragment().postValue(ManageHomeFragment.class);
    }

    @OnClick(R.id.btn_start_date)
    public void onBtnStartDateClicked() {
        btnStartDate.setEnabled(false);
        new DatePickerDialog(getActivity(), onStartDateSetListener, mYear, mMonth, mDay).show();
    }

    @OnClick(R.id.btn_end_date)
    public void onBtnEndDateClicked() {
        btnEndDate.setEnabled(false);
        new DatePickerDialog(getActivity(), onEndDateSetListener, mYear, mMonth, mDay).show();
    }

    private DatePickerDialog.OnDateSetListener onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            btnStartDate.setEnabled(true);
            btnStartDate.setText(new StringBuffer().append(year).append("-").
                    append(month + 1).append("-").append(dayOfMonth));
        }
    };
    private DatePickerDialog.OnDateSetListener onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            btnEndDate.setEnabled(true);
            btnEndDate.setText(new StringBuffer().append(year).append("-").
                    append(month + 1).append("-").append(dayOfMonth));
        }
    };

    @OnClick(R.id.btn_query)
    public void onViewClicked() {
        try {
            InParamPTDeliveryRecordQry inParam = new InParamPTDeliveryRecordQry();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (!Objects.equals("", btnStartDate.getText().toString()))
                inParam.BeginDate = sdf.parse(new StringBuffer().append(btnStartDate.getText().toString()).append(" 00:00:00").toString());
            else
                inParam.BeginDate = sdf.parse(new StringBuffer().append(mYear).append("-").
                        append(mMonth + 1).append("-").append(mDay).append(" 00:00:00").toString());
            if (!Objects.equals("", btnEndDate.getText().toString()))
                inParam.EndDate = sdf.parse(new StringBuffer().append(btnEndDate.getText().toString()).append(" 23:59:59").toString());
            else
                inParam.EndDate = sdf.parse(new StringBuffer().append(mYear).append("-").
                        append(mMonth + 1).append("-").append(mDay).append(" 23:59:59").toString());
            if (!Objects.equals("", etPhonoId.getText().toString()))
                inParam.CustomerMobile = etPhonoId.getText().toString();
            if (spPackageStatue.getSelectedItemId() != 4)
                inParam.PackageStatus = spPackageStatue.getSelectedItemId() + "";
            if (!Objects.equals("", etPackageId.getText().toString()))
                inParam.PackageID = etPackageId.getText().toString();
            if (!Objects.equals("", etCourierId.getText().toString()))
                inParam.PostmanID = etCourierId.getText().toString();
            manageViewmodel.manageModel.getIndeliveryRecordQry().setValue(inParam);
            manageViewmodel.queryDeliverRecords();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
