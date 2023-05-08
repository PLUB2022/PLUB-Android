package com.plub.presentation.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.ToastCompleteBinding
import com.plub.presentation.databinding.ToastErrorBinding

object PlubToast {

    fun createCompleteToast(context: Context, message: String, length : Int): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastCompleteBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_complete, null, false)

        binding.textViewMessage.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.px)
            duration = length
            view = binding.root
        }
    }

    fun createErrorToast(context: Context, message: String, length : Int): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastErrorBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_error, null, false)

        binding.textViewMessage.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.px)
            duration = length
            view = binding.root
        }
    }
}