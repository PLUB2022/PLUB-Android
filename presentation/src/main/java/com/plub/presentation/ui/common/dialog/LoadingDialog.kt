package com.plub.presentation.ui.common.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.DialogCommonBinding
import com.plub.presentation.databinding.DialogLoadingBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LoadingDialog @Inject constructor(@ActivityContext private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: DialogLoadingBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_loading, null, false)
    }

    private var dialog: AlertDialog? = null

    fun show() {
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun dismiss() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.removeView(binding.root)
        dialog?.dismiss()
    }
}