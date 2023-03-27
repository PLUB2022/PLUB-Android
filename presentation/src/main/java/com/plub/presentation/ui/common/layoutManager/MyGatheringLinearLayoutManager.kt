package com.plub.presentation.ui.common.layoutManager

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import kotlin.math.abs

class MyGatheringLinearLayoutManager(
    context: Context
) : LinearLayoutManager(context, HORIZONTAL, false) {

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChildren()
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return if (orientation == HORIZONTAL) {
            super.scrollHorizontallyBy(dx, recycler, state).also { scaleChildren() }
        } else {
            0
        }
    }

    private fun scaleChildren() {
        val parentMidPoint = width / 2f
        for (i in 0 until childCount) {
            val child = getChildAt(i) as View
            val childMidPoint = abs(parentMidPoint - (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f)

            val childFromParentDiffPercent = childMidPoint / parentMidPoint

            val viewShadow = child.findViewById<View>(R.id.view_shadow) ?: return
            viewShadow.alpha = childFromParentDiffPercent
        }
    }
}