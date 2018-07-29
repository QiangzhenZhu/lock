/**
 *
 */
package com.hzdongcheng.parcellocker.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自定义文本输入框
 */
public class RichEditText extends android.support.v7.widget.AppCompatEditText {


    private View mDecorView;
    private View mContentView;
    private boolean showCustomKeyBoard = true; // 是否启用自定义键盘
    private KeyboardUtils keyboardUtils;
    private int scrolldis = 50; // 输入框在键盘被弹出时，要被推上去的距离

    public static int screenw = -1;// 未知宽高
    public static int screenh = -1;
    public static int screenh_nonavbar = -1; // 不包含导航栏的高度
    public static int real_scontenth = -1; // 实际内容高度， 计算公式:屏幕高度-导航栏高度-电量栏高度

    public static float density = 1.0f;
    public static int densityDpi = 160;

    /**
     * @param context
     * @param attrs
     */
    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context);
        initKeyboard(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RichEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttributes(context);
        initKeyboard(context, attrs);
    }

    private void initKeyboard(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RichEditText);

        if (a.getBoolean(R.styleable.RichEditText_showKeyBoard, true)) {
            keyboardUtils = new KeyboardUtils();
        }
        if (KeyboardUtils.keyboardWindow != null) {
            KeyboardUtils.keyboardWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (scrolldis > 0) {
                        int temp = scrolldis;
                        scrolldis = 0;
                        if (null != mContentView) {
                            mContentView.scrollBy(0, -temp);
                        }
                    }
                }
            });
        }
        a.recycle();
    }

    public void showKeyboard() {
        if (keyboardUtils != null) {
            if (!keyboardUtils.isShowing()) {

                keyboardUtils.show(this);

                if (null != mDecorView && null != mContentView) {
                    int[] pos = new int[2];
                    // 计算弹出的键盘的尺寸
                    getLocationOnScreen(pos);
                    float height = dpToPx(getContext(), 240);


                    Rect outRect = new Rect();
                    // 然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域，
                    // * 包括标题栏，但不包括状态栏。
                    mDecorView.getWindowVisibleDisplayFrame(outRect);// 获得view空间，也就是除掉标题栏
                    // outRect.top表示状态栏（通知栏)
                    int screen = real_scontenth;
                    scrolldis = (int) ((pos[1] + getMeasuredHeight() - outRect.top) - (screen - height));

                    if (scrolldis > 0) {
                        mContentView.scrollBy(0, scrolldis);
                    }
                }

            }
        }
    }

    public void hideKeyboard() {
        if (null != keyboardUtils) {
            if (keyboardUtils.isShowing()) {
                keyboardUtils.dismiss();
            }
        }
    }

    private void hideSysInput() {
        if (this.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        requestFocus();
        requestFocusFromTouch();

        if (showCustomKeyBoard) {
            hideSysInput();
            showKeyboard();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != keyboardUtils) {
                if (keyboardUtils.isShowing()) {
                    keyboardUtils.dismiss();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getContext() instanceof Activity) {
            Window window = ((Activity) getContext()).getWindow();
            this.mContentView = window
                    .findViewById(Window.ID_ANDROID_CONTENT);
        } else
            mDecorView = getRootView();

        hideSysInput();

        if (this.hasFocus() && showCustomKeyBoard) {
            hideSysInput();
            showKeyboard();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hideKeyboard();
        mDecorView = null;
    }

    private void initAttributes(Context context) {
        this.setCursorVisible(true);
        initScreenParams(context);
        this.setLongClickable(false);
        this.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        removeCopyAbility();

        if (this.getText() != null) {
            this.setSelection(this.getText().length());
        }

        this.setCursorVisible(true);
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this, R.drawable.cursor);
        } catch (Exception ignored) {
        }
        this.setHintTextColor(Color.GRAY);

        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard();
                } else {
                    if (mDecorView != null && showCustomKeyBoard) {
                        hideSysInput();
                        showKeyboard();
                    }
                }
            }
        });

    }

    @TargetApi(11)
    private void removeCopyAbility() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }

                @Override
                public boolean onActionItemClicked(ActionMode mode,
                                                   MenuItem item) {
                    return false;
                }
            });
        }
    }

    /**
     * 密度转换为像素值
     *
     * @param dp
     * @return
     */
    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void initScreenParams(Context context) {
        DisplayMetrics dMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dMetrics);

        screenw = dMetrics.widthPixels;
        screenh = dMetrics.heightPixels;
        density = dMetrics.density;
        densityDpi = dMetrics.densityDpi;

        screenh_nonavbar = screenh;

        int ver = Build.VERSION.SDK_INT;

        // 新版本的android 系统有导航栏，造成无法正确获取高度
        if (ver == 13) {
            try {
                Method mt = display.getClass().getMethod("getRealHeight");
                screenh_nonavbar = (Integer) mt.invoke(display);
            } catch (Exception e) {
            }
        } else if (ver > 13) {
            try {
                Method mt = display.getClass().getMethod("getRawHeight");
                screenh_nonavbar = (Integer) mt.invoke(display);
            } catch (Exception e) {
            }
        }

        real_scontenth = screenh_nonavbar - getStatusBarHeight(context);

    }

    /**
     * 电量栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return sbar;
    }

}
