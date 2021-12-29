package com.archer.floatwindow.gesture

import android.view.MotionEvent

/**
 * Desc: 悬浮窗手势接口
 * <p>
 * Author: linjiaqiang
 * Date: 2021/12/28
 */
internal interface FloatGesture {

    /**
     * Desc: 触摸事件
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     */
    fun onTouchEvent(ev: MotionEvent): Boolean
}