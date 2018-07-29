package com.hzdongcheng.parcellocker.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryRecordQry;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageRecordAdapter extends BaseAdapter {

    ManageViewmodel manageViewmodel;
    public List<OutParamPTDeliveryRecordQry> outParam;
    public Context context;
    private LayoutInflater inflater;

    public ManageRecordAdapter(Context context, List<OutParamPTDeliveryRecordQry> outParamPTDeliveryRecord) {
        this.context = context;
        this.outParam = outParamPTDeliveryRecord;
        inflater = LayoutInflater.from(context);
        manageViewmodel = ViewModelProviders.of((FragmentActivity) context).get(ManageViewmodel.class);
    }

    @Override
    public int getCount() {
        return outParam == null ? 0 : outParam.size();
    }

    @Override
    public Object getItem(int position) {
        if (outParam == null)
            return null;
        else
            return outParam.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_manage_records, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.tvDeliverDate.setText(StringUtils.addQuote(outParam.get(position).StoredTime));
        holder.tvPackageNo.setText(outParam.get(position).PackageID);
        String pickupData = DateUtils.datetimeToString(outParam.get(position).TakedTime);
        if ("1900-01-01 00:00:00".equals(pickupData)) {
            holder.tvPickupDate.setText("--");
        } else {
            holder.tvPickupDate.setText(pickupData);
        }
        holder.tvBoxName.setText(outParam.get(position).BoxNo);
        holder.tvPackageStatus.setText(outParam.get(position).PackageStatusName);
        holder.tvUploadStatue.setText(outParam.get(position).UploadFlagName);
        if (Objects.equals("0", outParam.get(position).PackageStatus)) {
            holder.btnManagerPick.setVisibility(View.VISIBLE);
            holder.btnManagerPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageViewmodel.manageModel.getPackageId().setValue(outParam.get(position).PackageID);
                    manageViewmodel.managerPickUp();
                }
            });
        } else
            holder.btnManagerPick.setVisibility(View.INVISIBLE);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_deliver_date)
        TextView tvDeliverDate;
        @BindView(R.id.tv_package_no)
        TextView tvPackageNo;
        @BindView(R.id.tv_pickup_date)
        TextView tvPickupDate;
        @BindView(R.id.tv_box_name)
        TextView tvBoxName;
        @BindView(R.id.tv_package_status)
        TextView tvPackageStatus;
        @BindView(R.id.btn_manager_pick)
        Button btnManagerPick;
        @BindView(R.id.tv_upload_statue)
        TextView tvUploadStatue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
