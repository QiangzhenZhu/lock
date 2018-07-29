package com.hzdongcheng.parcellocker.utils;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 屏幕倒计时
 * Created by LJW on 2017/9/25.
 */

public class TimerUtils {
    Timer timer;
    TimerTask downCountTask;//倒计时任务
    TextView tv;
    Context context;
    TimeCount downCountTimer;
    int timeNum;//
    int downCount;//计数次数-秒

    long delayMillis = 1000;
    boolean isStart = false;//是否开始倒计时

    /**
     * 私有默认构造函数
     */
    private TimerUtils() {
        timer = new Timer();
        downCountTask = new TimerTask() {
            @Override
            public void run() {
                startCountTimer();
                System.gc();
                cancel();
            }
        };
    }

    private static class SingletonHolder {
        private static final TimerUtils instance = new TimerUtils();
    }

    /**
     * 静态工厂方法，返还此类的惟一实例
     *
     * @return
     */
    public static TimerUtils getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 默认倒计时60s
     *
     * @param tv
     */
    public void addCountDownTimer(TextView tv, Context context) {
        addCountDownTimer(tv, 60, context);//TODO debug zxy
    }

    /**
     * 设置超时时间
     *
     * @param
     */
    public void addCountDownTimer(int count) {
        if (this.tv != null && this.context != null) {
            addCountDownTimer(this.tv, count, this.context);
        }
    }

    public void addCountDownTimer(TextView tv, int count, Context context) {
        if (count < 1) {
            count = 1;
        }
        synchronized (this) {
            if (this.downCountTimer == null) {
                this.downCount = count;
                this.downCountTimer = new TimeCount(this.downCount * 1000, 1000);
            } else {
                removeCountDownTimer();
                if (count != this.downCount) {
                    this.downCount = count;
                    this.downCountTimer = new TimeCount(this.downCount * 1000, 1000);
                }
            }
        }
        this.tv = tv;
        this.context = context;
        //启动倒计时-默认延时
        startCountTimer(true);
    }

    protected void startCountTimer() {
        if (downCountTimer != null) {
            downCountTimer.cancel();
            downCountTimer.start();
        }
    }

    public void startCountTimer(long delayMillis) {

        if (delayMillis > 10) {
            this.delayMillis = delayMillis;
            //清除定时任务
            downCountTask.cancel();
            timer.purge();
            //新建定时任务
            downCountTask = new TimerTask() {
                @Override
                public void run() {
                    startCountTimer();
                    System.gc();
                    cancel();
                }
            };
            timer.schedule(downCountTask, this.delayMillis);
        } else {
            startCountTimer();
        }
    }

    /**
     * @param isDelay true 默认延时20s开始倒计时
     */
    public void startCountTimer(boolean isDelay) {
        if (isDelay) {
            startCountTimer(10000);//
        } else {
            startCountTimer();
        }
    }

    public void cancelCountDownTimer() {
        if (tv != null) {
            tv.setText("");
        }
        if (downCountTimer != null) {
            downCountTimer.cancel();
        }
    }

    public void removeCountDownTimer() {
        cancelCountDownTimer();
        if (tv != null) {
            tv.setVisibility(View.INVISIBLE);
            tv = null;
        }
    }

    public void destory() {
        timer.purge();
        timer.cancel();
    }

    public void onTouch() {
        //取消
        cancelCountDownTimer();
        //重新开始
        startCountTimer(this.delayMillis);
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {//计时完毕时触发
            if (tv != null) {
                tv.setText("");
            }
            ViewModelProviders.of((FragmentActivity) context).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.newInstance());
            isStart = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            if (tv != null) {
                isStart = true;
                tv.setVisibility(View.VISIBLE);
                tv.setText(millisUntilFinished / 1000 + "");
            } else {
                cancel();
                isStart = false;
            }
        }
    }
}
