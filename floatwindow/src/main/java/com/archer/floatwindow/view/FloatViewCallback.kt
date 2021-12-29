package com.archer.floatwindow.view

/**
 * Desc: 浮窗回调
 * <p>
 * Author: linjiaqiang
 * Date: 2021/12/28
 */
interface FloatViewCallback {

    /**
     * Desc: 位置更新
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/29
     */
    fun onLocationChange(x: Int, y: Int)

    /**
     * Desc: 点击
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/29
     */
    fun onTapUp()
}