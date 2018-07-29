package com.hzdongcheng.parcellocker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;


public class SpinnerArrayAdapter extends BaseAdapter {

    private  Context context;
    private String [] mStringArray;

    public SpinnerArrayAdapter(Context context, String[] objects) {
        this.context=context;
        mStringArray=objects;
    }



    @Override
    public int getCount() {
        return mStringArray.length;
    }

    @Override
    public Object getItem(int position) {
        return mStringArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView=LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item,null);
        if (convertView!=null) {
            TextView textView=(TextView) convertView.findViewById(android.R.id.text1);
            textView.setTextColor(context.getResources().getColor(R.color.colorInfo));
            textView.setTextSize(context.getResources().getDimension(R.dimen.prompt_info_size));
            textView.setText(mStringArray[position]);
            textView.setHeight((int) context.getResources().getDimension(R.dimen.edit_text_hegiht));
            textView.setGravity(Gravity.CENTER);
        }
        return convertView;
    }
}
