package com.hzdongcheng.parcellocker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.parcellocker.DBSApplication;
import com.hzdongcheng.parcellocker.model.FingerBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 指纹存储
 */
public class FingerprintCache {
    private final static String CACHE = "cache";

    public static List<FingerBean> getFinger() {
        List<FingerBean> ret = new ArrayList<>();
        SharedPreferences sharedPreferences = DBSApplication.getContext().getSharedPreferences(CACHE, Context.MODE_PRIVATE);
        Map<String, ?> all = sharedPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entries = all.entrySet();
        Type type = new TypeToken<List<FingerBean>>() {
        }.getType();
        for (Map.Entry<String, ?> item : entries) {
            List<FingerBean> fingerBean = (List<FingerBean>) PacketUtils.deserializeObject(item.getValue().toString(), type);
            ret.addAll(fingerBean);
        }
        return ret;
    }

    public static void clear(){
        SharedPreferences sharedPreferences = DBSApplication.getContext().getSharedPreferences(CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.apply();
    }

    public static List<FingerBean> getFinger(String postmanId) {
        List<FingerBean> ret = new ArrayList<>();
        SharedPreferences sharedPreferences = DBSApplication.getContext().getSharedPreferences(CACHE, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(postmanId, null);
        if (StringUtils.isNotEmpty(string)) {
            Type type = new TypeToken<List<FingerBean>>() {
            }.getType();
            ret = (List<FingerBean>) PacketUtils.deserializeObject(string, type);
        }
        return ret;
    }

    public static void setFinger(List<FingerBean> fingerBean) {
        if (fingerBean == null || fingerBean.size() < 1) {
            return;
        }
        SharedPreferences sharedPreferences = DBSApplication.getContext().getSharedPreferences(CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(fingerBean.get(0).pt, JsonUtils.toJSONString(fingerBean));
        edit.apply();

    }
}
