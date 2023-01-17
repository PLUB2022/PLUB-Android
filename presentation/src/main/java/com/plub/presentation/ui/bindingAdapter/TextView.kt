package com.plub.presentation.ui.bindingAdapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.plub.presentation.R
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.dp
import com.plub.presentation.util.px


@BindingAdapter("textViewPositionX")
fun TextView.bindTextViewPositionX(positionX: Float) {
    val display = this.resources?.displayMetrics
    val deviceWidth = display?.widthPixels ?: 0

    val newPositionX = positionX + 4.px

    when {
        newPositionX < 16.px -> this.x = 16.px.toFloat()
        newPositionX > deviceWidth - 74.px -> this.x = (deviceWidth - 74.px).toFloat()
        else -> this.x = newPositionX
    }
}