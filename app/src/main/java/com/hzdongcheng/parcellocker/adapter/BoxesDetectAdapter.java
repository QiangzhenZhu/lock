package com.hzdongcheng.parcellocker.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.BoxModel;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.views.widget.RecyclerItemsClickListener;

import java.util.List;

public class BoxesDetectAdapter extends RecyclerView.Adapter{
    private LayoutInflater inflater;
    private List<BoxModel> boxModelList;
    private WrapperFragment context;

    public BoxesDetectAdapter(WrapperFragment context, List<BoxModel> boxModelList) {
        this.boxModelList = boxModelList;
        if (context instanceof RecyclerItemsClickListener) {
            itemsClickListener = (RecyclerItemsClickListener) context;
        }
        inflater = LayoutInflater.from(context.getActivity());
        this.context=context;

    }

    public void changeBoxStatus(List<BoxModel> boxInfos){
        if (boxModelList.size()!=0) {
            int parent = 0;
            for (BoxModel boxModel : boxModelList) {
                for (BoxModel boxInfo : boxInfos) {
                    if (boxModel.boxId == boxInfo.boxId &&
                            (boxModel.door != boxInfo.door || boxModel.article != boxInfo.article)) {
                        boxModel.door = boxInfo.door;
                        boxModel.article = boxInfo.article;
                        final int finalParent = parent;
                        context.getView().post(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemChanged(finalParent);
                            }
                        });

                    }
                }
                parent++;
            }
        }
        else {
            boxModelList=boxInfos;
            notifyDataSetChanged();
        }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new UsualViewHolder(inflater.inflate(R.layout.item_box_usual, parent, false));
            case 1:
                return new LinearViewHolder(inflater.inflate(R.layout.item_box_linear, parent, false));
            default:
                return new UsualViewHolder(inflater.inflate(R.layout.item_box_usual, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (boxModelList.get(position).linear == 1) {
            LinearViewHolder viewHolder = (LinearViewHolder) holder;
        } else {
            final UsualViewHolder viewHolder = (UsualViewHolder) holder;
            viewHolder.tvBoxName.setText(boxModelList.get(position).boxName);
            viewHolder.ivBoxFault.setVisibility(boxModelList.get(position).fault ? View.VISIBLE : View.INVISIBLE);
            switch (boxModelList.get(position).door) {
                case 0:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.colorFocused));
                    break;
                case 1:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.havePackageStatusColor));
                    break;
                default:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.boxNoStatue));
            }
            switch (boxModelList.get(position).boxType) {
                case 8:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.boxControl));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorInfo));
                    viewHolder.ivBoxFault.setVisibility(View.INVISIBLE);
                    break;
                case 9:
                    viewHolder.itemView.getRootView().setBackgroundColor(context.getResources().getColor(R.color.boxControl));
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorInfo));
                    viewHolder.ivBoxFault.setVisibility(View.INVISIBLE);
                    break;
                default:
                    viewHolder.tvBoxName.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    viewHolder.ivBoxArticle.setVisibility(boxModelList.get(position).article == 1 ? View.VISIBLE : View.INVISIBLE);
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
