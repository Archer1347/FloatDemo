package com.archer.floatwindow.util

import androidx.annotation.Keep
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Desc: 基于arch.lifecycle机制的接口定义, 实现生命周期的注解
 *
 *
 * Author: linjiaqiang
 * Date: 2021/12/28
 */
@Keep
interface BaseLifecycle : LifecycleObserver {
    /**
     * 在LifecycleOwner的onCreate之后触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
    }

    /**
     * 在LifecycleOwner的onStart之后触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
    }

    /**
     * 在LifecycleOwner的onResume之后触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
    }

    /**
     * 在LifecycleOwner的onPause之前触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
    }

    /**
     * 在LifecycleOwner的onStop之前触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
    }

    /**
     * 在LifecycleOwner的onDestroy之前触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
    }

    /**
     * Desc:任意生命周期
     *
     *
     * Author: [lianyagang]
     * Date: 2019-12-23
     *
     * @param owner A class that has an Android lifecycle.
     * @param event 事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
    }
}