package com.archer.floatwindow

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.Px
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import com.archer.floatwindow.permission.requestOverlaysPermission
import com.archer.floatwindow.util.await
import com.archer.floatwindow.util.awaitLayout
import com.archer.floatwindow.util.rootView
import com.archer.floatwindow.view.FloatViewCallback
import com.archer.floatwindow.view.FloatViewImpl
import com.archer.floatwindow.window.FloatWindowImpl

/**
 * Desc: 悬浮弹窗管理
 * <p>
 * Author: linjiaqiang
 * Date: 2021/12/28
 */
object FloatWindowManager {

    /**
     * 存储所有的悬浮窗
     */
    private var floatViewImplList = mutableListOf<FloatViewImpl>()

    /**
     * Desc: 添加悬浮窗
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     *
     * @param activity 上下文activity
     * @param floatView 浮窗View
     * @param floatX 显示位置X
     * @param floatY 显示位置Y
     * @param fixedEdge 悬浮窗是否固定到屏幕边缘。如果移动到屏幕中间时释放，会移动到屏幕边缘
     * @param width 浮窗宽
     * @param height 浮窗高
     * @param onLocationChange 移动回调
     * @param onClick 点击
     */
    suspend fun addFloatView(
        activity: FragmentActivity,
        floatView: View,
        @Px floatX: Int,
        @Px floatY: Int,
        fixedEdge: Boolean = true,
        width: Int = WindowManager.LayoutParams.WRAP_CONTENT,
        height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
        onLocationChange: ((x: Int, y: Int) -> Unit)? = null,
        onClick: () -> Unit = { }
    ) {
        if (floatView.context is Activity) {
            throw IllegalArgumentException("不要使用activity创建View")
        }
        removeFloatView(floatView)
        // 全局弹窗需要先申请弹窗权限
        requestOverlaysPermission(activity)
        val context = activity.applicationContext
        val floatWindow = FloatWindowImpl(context)
        val floatViewImpl = FloatViewImpl(context)
        floatViewImpl.setView(floatView, floatWindow, fixedEdge, floatX, floatY, width, height)
        floatViewImpl.setFloatViewCallback(object : FloatViewCallback {
            override fun onLocationChange(x: Int, y: Int) {
                onLocationChange?.invoke(x, y)
            }

            override fun onTapUp() {
                onClick()
            }
        })
        floatViewImplList.add(floatViewImpl)
    }

    /**
     * Desc: 带动画移除悬浮窗
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/28
     */
    suspend fun removeFloatViewWithAnimator(floatView: View?) {
        floatView ?: return
        val floatViewImpl = findFloatViewImpl(floatView) ?: return
        ViewCompat.animate(floatView)
            .setDuration(300)
            .translationX((if (floatViewImpl.getFloatX() < floatView.width * 2) -floatView.width else (floatViewImpl.getFloatX() + floatView.width)).toFloat())
            .setInterpolator(AccelerateDecelerateInterpolator())
            .await()
        removeFloatView(floatView)
    }

    /**
     * Desc: 获取悬浮窗视图绑定的FloatViewImpl
     * <p>
     * Author: linjiaqiang
     * Date: 2021/12/27
     *
     * @param floatView 悬浮窗视图
     */
    private fun findFloatViewImpl(floatView: View): FloatViewImpl? {
        return floatViewImplList.firstOrNull { it.getView() == floatView }
    }

    /**
     * Desc: 移除悬浮窗
     * <p>
     * Author: linjiaqiang
     * Date: 2021/11/9
     */
    fun removeFloatView(floatView: View?) {
        floatView ?: return
        val floatViewImpl = findFloatViewImpl(floatView) ?: return
        floatViewImpl.removeView()
        floatViewImplList.remove(floatViewImpl)
    }

    /**
     * Desc: 全屏切换为小窗动画
     * <p>
     * Author: linjiaqiang
     * Date: 2021/10/29
     *
     * @param fragmentActivity 全屏的activity，主题必须透明
     * @param floatView 悬浮窗View
     */
    suspend fun switchToFloatWindow(fragmentActivity: FragmentActivity, floatView: View) {
        val floatViewImpl = findFloatViewImpl(floatView) ?: return
        // 浮窗渐变
        floatView.alpha = 0F
        floatView.awaitLayout()
        ViewCompat.animate(floatView)
            .setDuration(100)
            .alpha(1F)
            .setStartDelay(200)
            .start()

        val fullView = fragmentActivity.rootView
        // 全屏缩小
        val scale = minOf(floatView.width.toFloat() / fullView.width, floatView.height.toFloat() / fullView.height)
        if (floatViewImpl.getFloatX() > fullView.width / 2) {
            fullView.pivotX = fullView.width.toFloat()
        } else {
            fullView.pivotX = 0F
        }
        fullView.pivotY = (floatViewImpl.getFloatY() + floatView.height / 2).toFloat()
        ViewCompat.animate(fullView)
            .setDuration(300)
            .scaleX(scale)
            .scaleY(scale)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .alpha(0F)
            .await()
    }

}