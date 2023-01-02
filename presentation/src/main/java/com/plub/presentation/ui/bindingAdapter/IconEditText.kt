package com.plub.presentation.ui.bindingAdapter

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.plub.presentation.ui.custom.FixedTextWidthCheckBox
import com.plub.presentation.ui.custom.IconEditText

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
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            method(p0?.toString() ?: "")
        }
    })
}
