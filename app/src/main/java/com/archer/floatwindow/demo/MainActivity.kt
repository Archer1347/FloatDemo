package com.archer.floatwindow.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.archer.floatwindow.FloatWindowManager
import com.archer.floatwindow.util.getScreenWidth
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var isFloatWindowOpen = false

    private val floatView by lazy {
        ImageView(application.applicationContext).apply {
            setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_open_float_window).setOnClickListener {
            if (isFloatWindowOpen) {
                isFloatWindowOpen = false
                findViewById<Button>(R.id.btn_open_float_window).text = "打开悬浮窗"
                FloatWindowManager.removeFloatView(floatView)
            } else {
                openFloatWindow()
            }
        }
        findViewById<View>(R.id.btn_full_window).setOnClickListener {
            FloatWindowSwitcher.start(applicationContext)
        }
    }

    private fun openFloatWindow() {
        isFloatWindowOpen = true
        findViewById<Button>(R.id.btn_open_float_window).text = "关闭悬浮窗"
        lifecycleScope.launch {
            try {
                FloatWindowManager.addFloatView(
                    activity = this@MainActivity,
                    floatView = floatView,
                    floatX = getScreenWidth(this@MainActivity),
                    floatY = 500,
                    fixedEdge = findViewById<CheckBox>(R.id.checkbox).isChecked,
                    onLocationChange = { x, y ->
                        Log.e("floatWindow", "移动到:x=$x; y=$y")
                    },
                    onClick = {
                        Toast.makeText(this@MainActivity, "点击了悬浮窗", Toast.LENGTH_SHORT).show()
                    }
                )
            } catch (e: Throwable) {
                Toast.makeText(this@MainActivity, "打开悬浮窗失败 ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}