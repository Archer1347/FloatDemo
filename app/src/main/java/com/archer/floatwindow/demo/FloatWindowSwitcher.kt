package com.archer.floatwindow.demo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.archer.floatwindow.FloatWindowManager
import com.archer.floatwindow.util.getScreenWidth
import kotlinx.coroutines.*

/**
 * Desc: 悬浮窗和全屏切换的简单示例
 * <p>
 * Author: linjiaqiang
 * Date: 2021/12/29
 */
@SuppressLint("StaticFieldLeak")
object FloatWindowSwitcher {

    private var context: Context? = null
    private var floatView: View? = null
    private var fullWindowActivity: FragmentActivity? = null
    private var floatX: Int? = null
    private var floatY: Int? = null

    fun start(context: Context) {
        this.context = context.applicationContext
        switchToFullWindow()
    }

    fun stop() {
        GlobalScope.launch(SupervisorJob() + Dispatchers.Main) {
            releaseFullActivity()
            FloatWindowManager.removeFloatViewWithAnimator(floatView)
            floatView = null
            context = null
        }
    }

    fun switchToFullWindow() {
        val context = context ?: return
        val intent = Intent(context, FullFloatWindowActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (floatView == null) {
            context.startActivity(intent)
        } else {
            context.startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(floatView!!, 0, 0, floatView!!.width, floatView!!.height).toBundle())
        }
        FloatWindowManager.removeFloatView(floatView)
    }

    fun onActivityCreate(activity: FragmentActivity) {
        fullWindowActivity = activity
    }

    fun switchToFloatWindow() {
        val context = context ?: return
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Toast.makeText(context, "打开浮窗失败 -> ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
        GlobalScope.launch(SupervisorJob() + Dispatchers.Main + exceptionHandler) {
            val fullActivity = fullWindowActivity ?: return@launch
            if (floatView == null) {
                floatView = ImageView(context).apply { setImageResource(R.mipmap.ic_launcher) }
            }
            val floatView = floatView ?: return@launch
            FloatWindowManager.addFloatView(
                activity = fullActivity,
                floatView = floatView,
                floatX = floatX ?: getScreenWidth(context),
                floatY = floatY ?: 500,
                onLocationChange = { x, y ->
                    floatX = x
                    floatY = y
                },
                onClick = {
                    switchToFullWindow()
                }
            )
            FloatWindowManager.switchToFloatWindow(fullActivity, floatView)
            releaseFullActivity()
        }
    }

    private fun releaseFullActivity() {
        fullWindowActivity?.finish()
        fullWindowActivity?.overridePendingTransition(0, 0)
        fullWindowActivity = null
    }

}