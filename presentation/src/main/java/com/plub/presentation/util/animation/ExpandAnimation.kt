package com.plub.presentation.util.animation

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

object ExpandAnimation {

    fun toggleLayout(parentView : View, showView : View, isExpand : Boolean){
        if(isExpand){
            collapse(parentView, showView)
        }
        else{
            expand(parentView, showView)
        }
    }

    private fun collapse(view: View, showView : View) {
        val actualHeight = view.measuredHeight

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    showView.visibility = View.GONE
                } else {
                    view.layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt()
                    view.requestLayout()
                }
            }
        }

        animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }

    private fun expand(view: View, showView : View) {
        val animation = expandAction(view, showView)
        view.startAnimation(animation)
    }

    private fun expandAction(view: View, showView : View) : Animation {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val actualHeight = view.measuredHeight

        view.layoutParams.height = 0
        showView.visibility = View.VISIBLE

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                view.layoutParams.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
                else (actualHeight * interpolatedTime).toInt()

                view.requestLayout()
            }
        }

        animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)

        return animation
    }

}