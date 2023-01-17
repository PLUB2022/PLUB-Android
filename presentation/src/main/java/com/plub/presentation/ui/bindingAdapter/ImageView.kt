package com.plub.presentation.ui.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.plub.presentation.util.px


@BindingAdapter("imageViewPositionX")
fun ImageView.bindImageViewPositionX(positionX: Float) {
    this.x = positionX + 29.px
}