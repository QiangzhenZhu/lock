package com.hzdongcheng.parcellocker.adapter;

import android.content.ContentProvider;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.parcellocker.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickupListAdapter extends BaseAdapter {

    public void setPickupList(List<OutParamPTObtainPickupList> pickupList) {
        this.pickupList = pickupList;
        notifyDataSetChanged();
    }

    private List<OutParamPTObtainPickupList> pickupList;
    public Context context;
    private LayoutInflater inflater;

    public PickupListAdapter(Context context, List<OutParamPTObtainPickupList> outParams) {
        this.context = context;
        this.pickupList = outParams;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (pickupList != null) {
            return pickupList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (pickupList != null) {
            return pickupList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PickupListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pickup_list, null);
            holder = new PickupListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (PickupListAdapter.ViewHolder) convertView.getTag();

        holder.itemId.setText(pickupList.get(position).carUnique);
        holder.itemDesc.setText(pickupList.get(position).carModel);
        holder.itemState.setText(pickupList.get(position).reason);
        switch (pickupList.get(position).reason) {
            case "车辆出库":
                holder.itemState.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.item_out), null, null, null);
                break;
            case "移位":
                holder.itemState.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.item_shifting), null, null, null);
                break;
            default:
                holder.itemState.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.item_moment), null, null, null);
                break;
        }
        holder.itemState.setCompoundDrawablePadding(4);
        holder.itemChecked.setChecked(pickupList.get(position).isChecked);
        holder.position = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = ((PickupListAdapter.ViewHolder) view.getTag()).position;
                Log.i("Item", "position= " + p);
                toggleSelection(p);
            }
        });
        return convertView;
    }

    private void toggleSelection(int position) {
        pickupList.get(position).isChecked = !pickupList.get(position).isChecked;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.item_id)
        TextView itemId;
        @BindView(R.id.item_desc)
        TextView itemDesc;
        @BindView(R.id.item_state)
        TextView itemState;
        @BindView(R.id.item_checked)
        CheckBox itemChecked;

        int position;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
