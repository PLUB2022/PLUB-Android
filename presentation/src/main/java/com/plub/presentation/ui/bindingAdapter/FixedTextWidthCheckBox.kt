package com.plub.presentation.ui.bindingAdapter

import androidx.databinding.BindingAdapter
import com.plub.presentation.ui.custom.FixedTextWidthCheckBox

@BindingAdapter("android:onClick")
fun FixedTextWidthCheckBox.bindFixedTextWidthCheckboxClickEvent(method: () -> Unit) {
    this.setOnClickListener {
        isChecked = !isChecked
        method.invoke()
    }
}