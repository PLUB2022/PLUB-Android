package com.plub.presentation.util.animation

import android.view.View

object ArrowToggleAnimation {
    fun toggleArrow(view: View, isExpand: Boolean) {
        if (isExpand) {
            view.animate().setDuration(200).rotation(0f)
        } else {
            view.animate().setDuration(200).rotation(180f)
        }
    }
}