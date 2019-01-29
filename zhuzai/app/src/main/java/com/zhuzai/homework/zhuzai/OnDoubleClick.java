package com.zhuzai.homework.zhuzai;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class OnDoubleClick extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("get Here","get Here");
        return super.onDoubleTap(e);
    }
}
