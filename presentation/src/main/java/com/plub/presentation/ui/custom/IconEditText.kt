package com.plub.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
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

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_view_icon_edit_text, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconEditText, defStyleAttr, 0)

        iconResourceId = typedArray.getResourceId(R.styleable.IconEditText_icon, R.drawable.ic_location)
        binding.imageViewIc.setImageResource(iconResourceId)

        editTextHint = typedArray.getString(R.styleable.IconEditText_editTextHint)

        editTextHint?.apply {
            binding.editText.hint = this
        }

        editText = binding.editText

        typedArray.recycle()
    }
}