package com.hzdongcheng.parcellocker.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;

import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.parcellocker.DBSApplication;

/**
 * Created by Peace on 2017/9/22.
 */

public class ResourceUtils {

    /**
     * Return a resource value for the given error code,Return the code if no such resource was found.
     *
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        if (DBSApplication.getContext() != null) {
            try {
                return DBSApplication.getContext().getResources().getString(resId);
            } catch (Resources.NotFoundException e) {
                return getString("E" + resId);
            }
        }
        return String.valueOf(resId);
    }


    /**
     * Return a resource value for the given resource name,Return name if no such resource was found or no context setting.
     *
     * @param name
     * @return
     */
    public static String getString(String name) {
        name = name.replace("-", "E");
        if (DBSApplication.getContext() != null && !Character.isDigit(name.charAt(0))) {
            int resId = DBSApplication.getContext().getResources().getIdentifier(name, "string", DBSApplication.getContext().getPackageName());
            if (resId == 0) {
                return name;
            }
            return DBSApplication.getContext().getResources().getString(resId);
        }

        if (NumberUtils.isDigits(name)) {
            return getString(NumberUtils.parseInt(name));
        }
        return name;
    }
}
