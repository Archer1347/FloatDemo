package com.archer.floatwindow.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class FullFloatWindowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_window)
        FloatWindowSwitcher.onActivityCreate(this)
        findViewById<View>(R.id.btn_switch_to_float_window).setOnClickListener {
            FloatWindowSwitcher.switchToFloatWindow()
        }
        findViewById<View>(R.id.btn_stop).setOnClickListener {
            FloatWindowSwitcher.stop()
        }
    }

    override fun onBackPressed() {
    }
}