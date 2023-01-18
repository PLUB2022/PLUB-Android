package com.plub.presentation.util

import android.content.res.Resources

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = this / Resources.getSystem().displayMetrics.density

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.px: Float
    get() = this * Resources.getSystem().displayMetrics.density