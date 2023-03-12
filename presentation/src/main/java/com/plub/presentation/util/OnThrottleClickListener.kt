package com.plub.presentation.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 300L,  
) : View.OnClickListener {  
  
    private var clicked = false  
    override fun onClick(v: View?) {  
        if (!clicked) {  
            clicked = true  
            v?.run {  
                postDelayed(  
                    { clicked = false },  
                    interval,  
                )  
                clickListener.onClick(v)  
            }  
        }
    }  
}

fun View.onThrottleClick(
    onClick: (v: View) -> Unit,
) {
    val listener = View.OnClickListener { onClick(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun View.onThrottleClick(
    interval: Long,
    onClick: (v: View) -> Unit,
) {
    val listener = View.OnClickListener { onClick(it) }
    setOnClickListener(OnThrottleClickListener(listener, interval))
}