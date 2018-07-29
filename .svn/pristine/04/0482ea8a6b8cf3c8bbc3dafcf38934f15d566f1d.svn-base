package com.hzdongcheng.parcellocker.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryRecordQry;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class RecyclerRecordAdapter extends RecyclerView.Adapter{

    private Context mContext;
    DeliverViewmodel deliverViewmodel;
    private List<OutParamPTDeliveryRecordQry> outParams;

    public  RecyclerRecordAdapter(Context context, List<OutParamPTDeliveryRecordQry> data)
    {
        mContext=context;
        outParams=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_record, null);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) mContext).get(DeliverViewmodel.class);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecordViewHolder recordViewHolder=(RecordViewHolder) holder;
        final OutParamPTDeliveryRecordQry outParam=outParams.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        recordViewHolder.storedTime.setText(sdf.format(outParam.StoredTime));
        recordViewHolder.customer.setText(outParam.CustomerMobile);
        recordViewHolder.packageNo.setText(outParam.PackageID);
        if (Objects.equals("0",outParam.PackageStatus))
            recordViewHolder.packageStatus.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
        recordViewHolder.packageStatus.setText(outParam.PackageStatusName);
        recordViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    @Override
    public int getItemCount() {
        if (outParams != null) {
            return outParams.size();
        }
        return 0;

    }
    public class RecordViewHolder extends RecyclerView.ViewHolder {
        public TextView storedTime;
        public TextView customer;
        public TextView packageNo;
        public TextView packageStatus;
        public CardView cardView;

        public RecordViewHolder(View itemView) {
            super(itemView);
            storedTime = itemView.findViewById(R.id.tv_stored_time);
            customer =itemView.findViewById(R.id.tv_customer);
            packageNo =  itemView.findViewById(R.id.tv_package_no);
            packageStatus = itemView.findViewById(R.id.tv_package_status);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }

}
