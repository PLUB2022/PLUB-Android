package com.plub.presentation.ui.bindingAdapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.plub.presentation.R
import com.plub.presentation.util.dp


@BindingAdapter("textViewPositionX")
fun TextView.bindTextViewPositionX(positionX: Float) {
    this.x = positionX + 4.dp
    visibility = if(text == context.getString(R.string.create_gathering_people_number_seekbar_min) ||
        text == context.getString(R.string.create_gathering_people_number_seekbar_max))
        View.INVISIBLE
    else View.VISIBLE
}