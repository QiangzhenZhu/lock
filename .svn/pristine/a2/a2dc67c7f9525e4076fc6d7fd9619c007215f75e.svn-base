package com.hzdongcheng.parcellocker.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.BoxModel;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.ManageViewmodel;
import com.hzdongcheng.parcellocker.views.widget.RecyclerItemsClickListener;

import java.util.Iterator;
import java.util.List;

public class BoxesLayoutAdapter extends RecyclerView.Adapter{
    private LayoutInflater inflater;
    private List<BoxModel> boxModelList;
    private WrapperFragment context;
    ManageViewmodel manageViewmodel;

    public BoxesLayoutAdapter(WrapperFragment context, List<BoxModel> boxModelList) {
        this.boxModelList = boxModelList;
        if (context instanceof RecyclerItemsClickListener) {
            itemsClickListener = (RecyclerItemsClickListener) context;
        }
        inflater = LayoutInflater.from(context.getActivity());
        this.context=context;
        manageViewmodel = ViewModelProviders.of((FragmentActivity) context.getActivity()).get(ManageViewmodel.class);
    }

    /**
     * 设置箱门类型
     */
    public void setBoxSpen(int boxId,int boxSpen,String boxType){
        int parent=0;
        for (BoxModel boxModel:boxModelList){
            if (boxModel.boxId==boxId){
                boxModel.boxSpan=boxSpen;
                boxModel.boxType=Integer.parseInt(boxType);
                notifyItemChanged(parent);
            }
            parent++;
        }
        manageViewmodel.doSaveBoxMod(boxId,boxType);
    }
     /**
     * 设置箱门状态
     */
    public void setBoxStatue(int boxId,boolean boxStatue){
        int parent=0;
        for (BoxModel boxModel:boxModelList){
            if (boxModel.boxId==boxId){
                boxModel.fault=boxStatue;
                notifyItemChanged(parent);
            }
            parent++;
        }
        manageViewmodel.doSaveBoxStatueMod(boxId,boxStatue?"1":"0");
    }
    /**
     * 添加副柜
     */
    public void addSlaveCabinet(int boxCount){
        int lastBoxId=0;
        int lsatDeskId=0;
        int position=boxModelList.size();
        if (boxModelList.size()!=0){
            lastBoxId=boxModelList.get(boxModelList.size()-1).boxId;
            lsatDeskId=boxModelList.get(boxModelList.size()-1).deskId+1;
            BoxModel linear=new BoxModel();
            linear.linear=1;
            linear.deskId=lsatDeskId;
            boxModelList.add(linear);
        }
        for (int i=1;i<=boxCount;i++){
            BoxModel boxModel=new BoxModel();
            boxModel.boxId=lastBoxId+i;
            boxModel.boxName=boxModel.boxId+"";
            boxModel.boxSpan=1;
            boxModel.boxType=0;
            boxModel.deskType=manageViewmodel.manageModel.getCabinetType().getValue();
            boxModel.deskId=lsatDeskId;
            boxModelList.add(boxModel);
        }
        notifyItemRangeChanged(position,boxModelList.size()-position);
        manageViewmodel.doSaveCabinetAdd(boxCount);
    }
    /**
     * 移除最右边的副柜
     */
    public void deleteSlaveCabinet(){
        if (boxModelList.size()>0) {
            int deskId = boxModelList.get(boxModelList.size() - 1).deskId;
            Iterator<BoxModel> iterator = boxModelList.iterator();

            while (iterator.hasNext()) {
                BoxModel next = iterator.next();
                if (next.deskId == deskId) {
                    int parent = boxModelList.indexOf(next);
                    iterator.remove();
                    notifyItemRemoved(parent);
                }
            }
            manageViewmodel.doSaveCabinetDelete(deskId);
        }
    }
    public List<BoxModel> getCabinetInfo(){
        return this.boxModelList;
    }

    public int getCabinetInfoSize(){
        return this.boxModelList.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new BoxesLayoutAdapter.UsualViewHolder(inflater.inflate(R.layout.item_box_usual, parent, false));
            case 1:
                return new BoxesLayoutAdapter.LinearViewHolder(inflater.inflate(R.layout.item_box_linear, parent, false));
            default:
                return new BoxesLayoutAdapter.UsualViewHolder(inflater.inflate(R.layout.item_box_usual, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (boxModelList.get(position).linear == 1) {
            BoxesLayoutAdapter.LinearViewHolder viewHolder = (BoxesLayoutAdapter.LinearViewHolder) holder;
        } else {
            final BoxesLayoutAdapter.UsualViewHolder viewHolder = (BoxesLayoutAdapter.UsualViewHolder) holder;
            viewHolder.tvBoxName.setText(boxModelList.get(position).boxName);
            viewHolder.ivBoxFault.setVisibility(View.INVISIBLE);
            viewHolder.ivBoxFault.setVisibility(boxModelList.get(position).fault ? View.VISIBLE : View.INVISIBLE);
            switch (boxModelList.get(position).deskType){
                case 0:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.boxNoStatue));
                    break;
                case 1:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.coldControl));
                    break;
                case 2:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.heatControl));
                    break;
                case 3:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.washControl));
                    break;
                default:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.boxNoStatue));
            }

            switch (boxModelList.get(position).boxType) {
                case 0:
                    viewHolder.tvBoxName.setText(boxModelList.get(position).boxName+context.getString(R.string.prompt_manage_small_box));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    break;
                case 1:
                    viewHolder.tvBoxName.setText(boxModelList.get(position).boxName+context.getString(R.string.prompt_manage_middle_box));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    break;
                case 2:
                    viewHolder.tvBoxName.setText(boxModelList.get(position).boxName+context.getString(R.string.prompt_manage_large_box));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    break;
                case 3:
                    viewHolder.tvBoxName.setText(boxModelList.get(position).boxName+context.getString(R.string.prompt_manage_Super_box));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    break;
                case 4:
                    viewHolder.tvBoxName.setText(boxModelList.get(position).boxName+context.getString(R.string.prompt_manage_mini_box));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    break;
                case 8:
                    viewHolder.tvBoxName.setText(boxModelList.get(position).boxName+context.getString(R.string.prompt_manage_adv_box));
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.boxControl));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorInfo));
                    break;
                case 9:
                    viewHolder.tvBoxName.setText(boxModelList.get(position).boxName+context.getString(R.string.prompt_manage_control_box));
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.boxControl));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorInfo));
                    break;
                default:
                    viewHolder.itemView.getRootView().setBackgroundColor(Color.parseColor("#003322"));
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemsClicked(viewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return boxModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return boxModelList.get(position).linear;
    }

    /**
     * 副柜分割线
     */
    class LinearViewHolder extends RecyclerView.ViewHolder {

        LinearViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 普通快递格口
     */
    class UsualViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBoxFault;
        TextView tvBoxName;
        ImageView ivBoxArticle;

        UsualViewHolder(View itemView) {
            super(itemView);
            ivBoxArticle = itemView.findViewById(R.id.iv_box_article);
            ivBoxFault = itemView.findViewById(R.id.iv_box_fault);
            tvBoxName = itemView.findViewById(R.id.tv_box_name);
        }
    }

    private RecyclerItemsClickListener itemsClickListener;

    private void notifyItemsClicked(int position) {
        if (itemsClickListener != null) {
            itemsClickListener.onClick(position);
        }
    }

}
