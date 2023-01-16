package com.plub.presentation.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import java.io.File

object GlideUtil {
    fun loadImage(context: Context,url: String,view: ImageView) {
        load(context, url, view)
    }

    fun loadImage(context: Context,url: String,view: ImageView, placeHolder: Int) {
        load(context, url, view, placeHolder)
    }

    fun loadImage(context: Context,url: String,view: ImageView,listener: RequestListener<Drawable>) {
        load(context, url, view,listener = listener)
    }

    fun loadImage(context: Context, file: File, view: ImageView) {
        load(context, file, view)
    }

    private fun load(
        context: Context,
        url: String,
        view: ImageView,
        placeHolder: Int? = null,
        listener: RequestListener<Drawable>? = null
    ) {
        Glide.with(context).load(url).listener(listener).apply {
            placeHolder?.let {
                error(it).placeholder(it)
            }
            into(view)
        }
    }

    private fun load(
        context: Context,
        file: File,
        view: ImageView,
        placeHolder: Int? = null,
        listener: RequestListener<Drawable>? = null
    ) {
        Glide.with(context).load(file).listener(listener).apply {
            placeHolder?.let {
                error(it).placeholder(it)
            }
            into(view)
        }
    }
}