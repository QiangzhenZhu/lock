package com.hzdongcheng.parcellocker.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentUtils {
    public static void add(FragmentManager fm, Fragment add, int id) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(id, add);
        ft.commitAllowingStateLoss();
    }
}
