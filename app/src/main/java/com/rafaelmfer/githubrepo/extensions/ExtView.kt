package com.rafaelmfer.githubrepo.extensions

import android.os.SystemClock
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(DelicateCoroutinesApi::class)
fun View.onSingleClick(
    delayTime: Long = 1000L,
    context: CoroutineContext = Dispatchers.Main,
    handler: suspend CoroutineScope.(v: View) -> Unit
) {
    var lastClickTime = 0L

    setOnClickListener { v ->
        if (SystemClock.elapsedRealtime() - lastClickTime >= delayTime) {
            lastClickTime = SystemClock.elapsedRealtime()

            GlobalScope.launch(context, CoroutineStart.DEFAULT) {
                handler(v)
            }
        }
    }
}

val View.visible: View
    get() {
        visibility = View.VISIBLE
        return this
    }

val View.invisible: View
    get() {
        visibility = View.INVISIBLE
        return this
    }

val View.gone: View
    get() {
        visibility = View.GONE
        return this
    }