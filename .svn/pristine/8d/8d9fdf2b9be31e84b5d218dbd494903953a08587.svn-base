package com.hzdongcheng.parcellocker.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.TextViewCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdongcheng.parcellocker.DBSApplication;

import butterknife.internal.Utils;

/**
 * Created by dcdz on 2017/10/25.
 */

public class ToastUtils {
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
    private static int xOffset = 0;
    private static int yOffset = -65;
    private static int bgColor = COLOR_DEFAULT;
    private static int bgResource = -1;
    private static int msgColor = COLOR_DEFAULT;

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast myToast;

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        // 这里给了一个1/4屏幕高度的y轴偏移量
        toast.setGravity(Gravity.TOP, 0, 80);
        return toast;
    }

    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x 偏移
     * @param yOffset y 偏移
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtils.bgColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtils.bgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param msgColor 颜色
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        ToastUtils.msgColor = msgColor;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源 Id
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showLong(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源 Id
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    private static void show(@StringRes final int resId, final int duration) {
        show(ResourceUtils.getString(resId), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                myToast = Toast.makeText(DBSApplication.getContext(), text, duration);
                // solve the font of toast
                TextView tvMessage = myToast.getView().findViewById(android.R.id.message);
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                tvMessage.setTextColor(msgColor);
                myToast.setGravity(gravity, xOffset, yOffset);
                setBg(tvMessage);
                myToast.show();
            }
        });
    }

    private static void setBg(final TextView tvMsg) {
        View toastView = myToast.getView();
        if (bgResource != -1) {
            toastView.setBackgroundResource(bgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(bgColor);
            }
        }
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (myToast != null) {
            myToast.cancel();
            myToast = null;
        }
    }

}
