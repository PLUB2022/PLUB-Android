package com.plub.presentation.ui.common.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition: Int = parent.getChildAdapterPosition(view)
        val itemCount: Int = state.itemCount
        val isLastCell = itemCount > 0 && itemPosition == itemCount - 1
        if (!isLastCell) outRect.bottom = verticalSpaceHeight
    }
}