package com.plub.presentation.ui.common.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import android.graphics.Color
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.DialogCommonBinding
import com.plub.presentation.util.onThrottleClick
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import javax.inject.Singleton

class CommonDialog @Inject constructor(@ActivityContext private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: DialogCommonBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_common, null, false)
    }

    private var dialog: AlertDialog? = null

    fun setTitle(@StringRes messageId: Int): CommonDialog {
        binding.textViewTitle.apply {
            text = context.getText(messageId)
        }
        return this
    }

    fun setTitle(message: CharSequence): CommonDialog {
        binding.textViewTitle.apply {
            text = message
        }
        return this
    }

    fun setDescription(@StringRes messageId: Int?): CommonDialog {
        binding.textViewDescription.apply {
            if(messageId == null) visibility = View.GONE
            else {
                visibility = View.VISIBLE
                text = context.getText(messageId)
            }
        }
        return this
    }

    fun setDescription(message: CharSequence): CommonDialog {
        binding.textViewDescription.apply {
            if(message.isEmpty()) visibility = View.GONE
            else {
                visibility = View.VISIBLE
                text = message
            }
        }
        return this
    }

    fun setGoneDescription(): CommonDialog{
        binding.textViewDescription.visibility = View.GONE
        return this
    }

    fun setPositiveButton(@StringRes textId: Int, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.buttonPositive.apply {
            text = context.getText(textId)
            onThrottleClick(onClickListener)
            dismiss()
        }
        return this
    }

    fun setPositiveButton(text: CharSequence, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.buttonPositive.apply {
            this.text = text
            onThrottleClick(onClickListener)
            dismiss()
        }
        return this
    }

    fun setNegativeButton(@StringRes textId: Int, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.buttonNegative.apply {
            visibility = View.VISIBLE
            text = context.getText(textId)
            this.text = text
            onThrottleClick(onClickListener)
        }
        return this
    }

    fun setNegativeButton(text: CharSequence, onClickListener: (view: View) -> (Unit)): CommonDialog {
        binding.buttonNegative.apply {
            visibility = View.VISIBLE
            this.text = text
            onThrottleClick(onClickListener)
        }
        return this
    }


    fun create() {
        dialog = builder.create()
    }

    fun show() {
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.show()
    }

    fun dismiss() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.removeView(binding.root)
        dialog?.dismiss()
    }
}