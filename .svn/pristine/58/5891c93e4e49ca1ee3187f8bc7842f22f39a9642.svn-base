package com.hzdongcheng.parcellocker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.parcellocker.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickupOpenedAdapter extends BaseAdapter {
    public void setPickupLists(List<OutParamPTObtainPickupList> pickupLists) {
        this.pickupLists = pickupLists;
    }

    private List<OutParamPTObtainPickupList> pickupLists;
    public Context context;
    private LayoutInflater inflater;

    public PickupOpenedAdapter(Context context, List<OutParamPTObtainPickupList> outParams) {
        this.context = context;
        this.pickupLists = outParams;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (pickupLists != null) {
            return pickupLists.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_pickup_opend, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.tvBoxId.setText(pickupLists.get(i).boxName);
        viewHolder.itemId.setText(pickupLists.get(i).carUnique);
        viewHolder.itemDesc.setText(pickupLists.get(i).carModel);
        if (pickupLists.get(i).retryCount == 0) {
            viewHolder.btRetry.setVisibility(View.INVISIBLE);
            viewHolder.btTakeOut.setVisibility(View.INVISIBLE);
            viewHolder.tvTips.setText("等待开箱");
        } else {
            if (pickupLists.get(i).isOpened && pickupLists.get(i).status == 1) {
                viewHolder.btRetry.setVisibility(View.INVISIBLE);
                viewHolder.btTakeOut.setVisibility(View.INVISIBLE);
                viewHolder.tvTips.setVisibility(View.VISIBLE);
                viewHolder.tvTips.setText("已取出");
            } else if (!pickupLists.get(i).isOpened && pickupLists.get(i).status == 2) {
                viewHolder.btRetry.setVisibility(View.INVISIBLE);
                viewHolder.btTakeOut.setVisibility(View.INVISIBLE);
                viewHolder.tvTips.setVisibility(View.VISIBLE);
                viewHolder.tvTips.setText("已放弃取用");
            } else {
                viewHolder.btRetry.setVisibility(View.VISIBLE);
                viewHolder.btTakeOut.setVisibility(View.VISIBLE);
                viewHolder.tvTips.setVisibility(View.GONE);
            }
            viewHolder.btTakeOut.setTag(i);
            viewHolder.btRetry.setTag(i);
            viewHolder.btTakeOut.setOnClickListener(onClickListener);
            viewHolder.btRetry.setOnClickListener(onClickListener);
        }


        return view;
    }

    public void setListener(OnAdapterInteractionListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onAdapterInteraction(view);
            }
        }
    };

    private OnAdapterInteractionListener listener;


    public interface OnAdapterInteractionListener {
        void onAdapterInteraction(View view);
    }

    static class ViewHolder {
        @BindView(R.id.tv_box_id)
        TextView tvBoxId;
        @BindView(R.id.item_id)
        TextView itemId;
        @BindView(R.id.item_desc)
        TextView itemDesc;
        @BindView(R.id.bt_retry)
        Button btRetry;
        @BindView(R.id.bt_take_out)
        Button btTakeOut;
        @BindView(R.id.tv_tips)
        TextView tvTips;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
