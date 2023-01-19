package com.plub.presentation.ui.bindingAdapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.px
import java.io.File


@BindingAdapter("imageViewPositionX")
fun ImageView.bindImageViewPositionX(positionX: Float) {
    this.x = positionX + 29.px
}

@BindingAdapter("imageFile","defaultImage","radiusDp")
fun ImageView.setImageFile(imageFile: File?, defaultImage: Drawable, radius: Int) {
    if(imageFile == null) setImageDrawable(defaultImage)
    else { GlideUtil.loadRadiusImage(context,imageFile,this, radius) }
}