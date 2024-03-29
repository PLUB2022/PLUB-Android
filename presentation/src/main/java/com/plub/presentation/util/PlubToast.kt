package com.plub.presentation.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.plub.domain.model.enums.ToastType
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutToastBinding

object PlubToast {

    const val MARGIN_BOTTOM = 16

    fun createToast(type : ToastType, context: Context, message: String, length : Int = Toast.LENGTH_LONG): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: LayoutToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_toast, null, false)

        when(type){
            ToastType.COMPLETE -> binding.imageViewIcon.setImageResource(R.drawable.ic_circle_check)
            ToastType.ERROR -> binding.imageViewIcon.setImageResource(R.drawable.ic_circle_error)
        }
        binding.textViewMessage.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, MARGIN_BOTTOM.px)
            duration = length
            view = binding.root
        }
    }

    fun createToast(type : ToastType, context: Context, message: Int, length : Int = Toast.LENGTH_LONG): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: LayoutToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_toast, null, false)

        when(type){
            ToastType.COMPLETE -> binding.imageViewIcon.setImageResource(R.drawable.ic_circle_check)
            ToastType.ERROR -> binding.imageViewIcon.setImageResource(R.drawable.ic_circle_error)
        }
        binding.textViewMessage.setText(message)

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, MARGIN_BOTTOM.px)
            duration = length
            view = binding.root
        }
    }
}