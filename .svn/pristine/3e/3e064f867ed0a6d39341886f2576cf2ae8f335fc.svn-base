package com.hzdongcheng.parcellocker.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

import com.hzdongcheng.parcellocker.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Peace on 17/9/21.
 */

public class SoundUtils {

    SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;
    private static SoundUtils instance = null;

    public static SoundUtils getInstance() {
        if (instance == null) {
            synchronized (SoundUtils.class) {
                if (instance == null)
                    instance = new SoundUtils();
            }
        }
        return instance;
    }

    private SoundUtils() {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundPool.autoPause();
        soundPoolMap = new LinkedHashMap<>();
    }

    public void loadSound(Context context) {
//        soundPoolMap.put(1, soundPool.load(context, R.raw.v01, 2));
//        soundPoolMap.put(2, soundPool.load(context, R.raw.v02, 2));
//        soundPoolMap.put(3, soundPool.load(context, R.raw.v03, 2));
//        soundPoolMap.put(4, soundPool.load(context, R.raw.v04, 2));
//        soundPoolMap.put(5, soundPool.load(context, R.raw.v05, 2));
//        soundPoolMap.put(6, soundPool.load(context, R.raw.v06, 2));
//        soundPoolMap.put(7, soundPool.load(context, R.raw.v07, 2));
//        soundPoolMap.put(8, soundPool.load(context, R.raw.v08, 2));
//        soundPoolMap.put(9, soundPool.load(context, R.raw.v09, 2));
//        soundPoolMap.put(10, soundPool.load(context, R.raw.v10, 2));
//        soundPoolMap.put(11, soundPool.load(context, R.raw.v11, 2));
//        soundPoolMap.put(12, soundPool.load(context, R.raw.v12, 2));
//        soundPoolMap.put(13, soundPool.load(context, R.raw.v13, 2));
//        soundPoolMap.put(14, soundPool.load(context, R.raw.v14, 2));
//        soundPoolMap.put(15, soundPool.load(context, R.raw.v15, 2));
//        soundPoolMap.put(16, soundPool.load(context, R.raw.v16, 2));
//        soundPoolMap.put(17, soundPool.load(context, R.raw.v17, 1));
//        soundPoolMap.put(18, soundPool.load(context, R.raw.v18, 1));
//        soundPoolMap.put(19, soundPool.load(context, R.raw.v19, 1));
//        soundPoolMap.put(20, soundPool.load(context, R.raw.v20, 1));
//        soundPoolMap.put(21, soundPool.load(context, R.raw.v21, 1));
//        soundPoolMap.put(22, soundPool.load(context, R.raw.v22, 1));
//        soundPoolMap.put(23, soundPool.load(context, R.raw.v23, 1));
//        soundPoolMap.put(24, soundPool.load(context, R.raw.v24, 1));
//        soundPoolMap.put(25, soundPool.load(context, R.raw.v25, 2));
//        soundPoolMap.put(26, soundPool.load(context, R.raw.v26, 2));
//        soundPoolMap.put(27, soundPool.load(context, R.raw.v27, 2));
//        soundPoolMap.put(28, soundPool.load(context, R.raw.v28, 2));
//        soundPoolMap.put(29, soundPool.load(context, R.raw.v29, 2));
//        soundPoolMap.put(30, soundPool.load(context, R.raw.v30, 2));
//        soundPoolMap.put(31, soundPool.load(context, R.raw.v31, 2));
//        soundPoolMap.put(99, soundPool.load(context, R.raw.button, 2));
    }

    public void play(int resId) {
        Integer id = soundPoolMap.get(resId);
        if (null != id)
            soundPool.play(id, 1, 1, 1, 0, 1);
    }

    public void destroy() {
        soundPool.release();
    }
}
