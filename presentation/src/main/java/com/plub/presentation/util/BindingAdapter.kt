package com.plub.presentation.util

import android.graphics.drawable.Drawable
import android.view.View
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.plub.domain.UiState
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

@BindingAdapter("imageFile","defaultImage")
fun ImageView.setImageFile(imageFile:File?, defaultImage:Drawable) {
    if(imageFile == null) setImageDrawable(defaultImage)
    else { GlideUtil.loadImage(context,imageFile,this) }
}

@BindingAdapter("hintIcon")
fun EditText.setHintIcon(icon:Int) {
    setOnFocusChangeListener { view, gotFocus ->
        when {
            gotFocus -> setCompoundDrawables(null,null,null,null)
            text.isEmpty() -> setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0)
        }
    }
}