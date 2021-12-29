package com.archer.floatwindow.window

import android.view.View

/**
 * Desc: 悬浮弹窗window
 * <p>
 * Author: linjiaqiang
 * Date: 2021/12/28
 */
interface FloatWindow {

    /**
     * Desc: 为悬浮弹窗添加View
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     */
    fun setView(floatView: View, width: Int?, height: Int?)

    /**
     * Desc: 移除View
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     */
    fun removeView(floatView: View)

    /**
     * Desc: 更新View的位置
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     */
    fun updateView(floatView: View, x: Int, y: Int)

}