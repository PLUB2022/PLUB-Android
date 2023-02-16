package com.plub.presentation.ui.common.bindingAdapter

import android.graphics.drawable.Drawable
import android.view.View
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.plub.domain.UiState
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.afterTextChanged
import java.io.File

@BindingAdapter("showLoadingProgressBar")
fun ProgressBar.bindShow(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("showErrorPage")
fun View.bindShowErrorPage(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Error) View.VISIBLE else View.GONE
}

@BindingAdapter("loadUrl")
fun loadUrl(view: WebView, url: String) {
    if(view.url != url) view.loadUrl(url)
}

@BindingAdapter("imageFile")
fun ImageView.setImageFile(imageFile:File?) {
    if(imageFile == null) return
    else {
        GlideUtil.loadImage(context, imageFile, this)
    }
}

@BindingAdapter("imageFile","defaultImage")
fun ImageView.setImageFile(imageFile:File?, defaultImage:Drawable) {
    if(imageFile == null) setImageDrawable(defaultImage)
    else {
        GlideUtil.loadImage(context, imageFile, this)
    }
}

@BindingAdapter("hintIcon")
fun EditText.setHintIcon(icon:Int) {
    afterTextChanged {
        when {
            text.isEmpty() -> setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0)
            else -> setCompoundDrawables(null,null,null,null)
        }
    }
}