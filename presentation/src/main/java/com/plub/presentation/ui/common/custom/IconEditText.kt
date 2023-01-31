package com.plub.presentation.ui.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.CustomViewIconEditTextBinding


class IconEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomViewIconEditTextBinding
    private val iconResourceId: Int
    private val editTextHint: String?
    val editText: EditText
        get() = binding.editText

    val icon: ImageView
        get() = binding.imageViewIc

    var editTextValue: String = ""
        set(value) {
            val old = editText.text.toString()
            if(old != value) {
                field = value
                editText.setText(editTextValue)
            }
        }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_view_icon_edit_text, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconEditText, defStyleAttr, 0)

        iconResourceId = typedArray.getResourceId(R.styleable.IconEditText_iconEditTextIcon, R.drawable.ic_location_inactive)
        binding.imageViewIc.setImageResource(iconResourceId)

        editTextHint = typedArray.getString(R.styleable.IconEditText_editTextHint)

        editTextHint?.apply {
            binding.editText.hint = this
        }

        val imeOption: Int = typedArray.getInt(R.styleable.IconEditText_android_imeOptions, EditorInfo.IME_ACTION_NONE)
        binding.editText.imeOptions = imeOption
        if (imeOption == EditorInfo.IME_ACTION_SEARCH) {
            binding.editText.setSingleLine()
        }

        typedArray.recycle()
    }
}