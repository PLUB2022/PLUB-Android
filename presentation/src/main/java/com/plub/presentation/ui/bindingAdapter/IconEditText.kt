package com.plub.presentation.ui.bindingAdapter

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.plub.presentation.ui.custom.FixedTextWidthCheckBox
import com.plub.presentation.ui.custom.IconEditText
import com.plub.presentation.util.afterTextChanged

@BindingAdapter("iconEditTextActionEvent")
fun IconEditText.bindEditTextActionEvent(method: () -> Void) {
    editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
        override fun onEditorAction(
            textView: TextView?,
            actionId: Int,
            keyEvent: KeyEvent?
        ): Boolean {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                method()
                return true
            }
            return false
        }
    }
    )
}

@BindingAdapter("updateIconEditText")
fun IconEditText.bindUpdateIconEditText(method: (text: String) -> Unit) {
    editText.afterTextChanged { text ->
        method(text?.toString() ?: "")
    }
}

@BindingAdapter(value = ["hintTextColor", "defaultTextColor"], requireAll = true)
fun IconEditText.bindSetIconEditTextHintColor(
    hintTextColor: Int,
    defaultTextColor: Int
) {
    editText.setHintTextColor(hintTextColor)
    editText.setTextColor(defaultTextColor)
}

@BindingAdapter(value = ["hintBackground", "defaultBackground"], requireAll = true)
fun IconEditText.bindSetIconEditTextHintBackground(
    hintBackground: Drawable,
    defaultBackground: Drawable
) {
    background = hintBackground
    editText.afterTextChanged {
        background = if(editText.text.isEmpty()) hintBackground
        else defaultBackground
    }
}
