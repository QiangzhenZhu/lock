package com.hzdongcheng.parcellocker.utils;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.XmlRes;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.hzdongcheng.parcellocker.DBSApplication;
import com.hzdongcheng.parcellocker.R;

import java.util.List;


public class KeyboardUtils implements KeyboardView.OnKeyboardActionListener {
    private static final int KEYCODE_MODE_NUMBER = 0;
    private static final int KEYCODE_MODE_LETTER = 1;
    private static final int KEYCODE_MODE_MARK = 2;
    private static Keyboard qwerty;
    private static Keyboard number;
    private static Keyboard mark;
    private  KeyboardView keyboardView;
    public static PopupWindow keyboardWindow;
    private boolean isSupper = false;
    private int mTemplate;
    private EditText editText;

    public KeyboardUtils() {
        if (DBSApplication.getContext() == null) {
            Log.i("key", "没有获取到Context，键盘初始化停止");
            return;
        }
        if (qwerty == null) {
            number = new Keyboard(DBSApplication.getContext(), R.xml.symbols);
            mark = new Keyboard(DBSApplication.getContext(), R.xml.punctuate);
            qwerty = new Keyboard(DBSApplication.getContext(), R.xml.qwerty);
        }

        if (keyboardWindow == null) {
            View contentView = LayoutInflater.from(DBSApplication.getContext()).inflate(R.layout.keyboard_view, null);
            keyboardView = contentView.findViewById(R.id.keyboard_view);
            keyboardView.setPreviewEnabled(false);
            keyboardView.setKeyboard(qwerty);
            keyboardView.setOnKeyboardActionListener(this);
            keyboardWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            ViewTreeObserver viewTreeObserver = keyboardView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (keyboardWindow.isShowing())
                        keyboardWindow.update(ViewGroup.LayoutParams.MATCH_PARENT, keyboardView.getMeasuredHeight());
                }
            });
        } else {
            keyboardView = keyboardWindow.getContentView().findViewById(R.id.keyboard_view);
        }
    }

    public boolean isShowing() {
        return keyboardWindow != null && keyboardWindow.isShowing();
    }

    public void show(EditText editText) {
        this.editText = editText;
        if (editText.getInputType() == InputType.TYPE_CLASS_TEXT) {
            keyboardView.setKeyboard(qwerty);
        } else {
            keyboardView.setKeyboard(number);
        }
        keyboardView.setOnKeyboardActionListener(this);
        if (!keyboardWindow.isShowing()) {
            keyboardWindow.showAtLocation(editText.getRootView(), Gravity.BOTTOM, 0, 0);
        }
    }


    public void dismiss() {
        if (keyboardWindow.isShowing()) {
            keyboardWindow.dismiss();
        }
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        if (i == Keyboard.KEYCODE_SHIFT) {// 大小写切换
            changeKey();
            keyboardView.setKeyboard(qwerty);
        } else if (i == Keyboard.KEYCODE_MODE_CHANGE) {
            if (mTemplate == KEYCODE_MODE_NUMBER) {
                mTemplate = KEYCODE_MODE_LETTER;
                keyboardView.setKeyboard(qwerty);
            } else {
                mTemplate = KEYCODE_MODE_NUMBER;
                keyboardView.setKeyboard(number);
            }
        } else if (i == Keyboard.KEYCODE_ALT) {
            if (mTemplate == KEYCODE_MODE_NUMBER) {
                mTemplate = KEYCODE_MODE_MARK;
                keyboardView.setKeyboard(mark);
            } else {
                mTemplate = KEYCODE_MODE_NUMBER;
                keyboardView.setKeyboard(number);
            }
        } else if (i == Keyboard.KEYCODE_DELETE) {
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        } else if (i == Keyboard.KEYCODE_DONE) {
            dismiss();
        } else if (i == 57419) { // go left
            if (start > 0) {
                editText.setSelection(start - 1);
            }
        } else if (i == 57421) { // go right
            if (start < editText.length()) {
                editText.setSelection(start + 1);
            }
        } else {
            editable.insert(start, Character.toString((char) i));
        }
    }

    private void changeKey() {
        List<Keyboard.Key> keys = qwerty.getKeys();
        if (isSupper) {// 大写切换小写
            isSupper = false;
            for (Keyboard.Key key : keys) {
                if (key.label != null && isLetter(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {// 小写切换大写
            isSupper = true;
            for (Keyboard.Key key : keys) {
                if (key.label != null && isLetter(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    private boolean isLetter(String s) {
        if (s.length() == 1) {
            int ascii = s.charAt(0);
            return (ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122);
        }
        return false;
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
