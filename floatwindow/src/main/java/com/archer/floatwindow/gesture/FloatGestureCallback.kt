package com.archer.floatwindow.gesture

/**
 * Desc: 悬浮窗手势回调
 * <p>
 * Author: linjiaqiang
 * Date: 2021/12/28
 */
internal interface FloatGestureCallback {

    /**
     * Desc: 移动
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     *
     * @param dx x偏移
     * @param dy y偏移
     */
    fun onMove(dx: Int, dy: Int)

    /**
     * 开始移动
     */
    fun onMoveStart() {}

    /**
     * 移动结束
     */
    fun onMoveEnd() {}

    /**
     * Desc: 点击
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     */
    fun onTapUp() {}
}