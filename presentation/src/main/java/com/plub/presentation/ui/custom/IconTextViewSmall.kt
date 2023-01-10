package com.plub.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.CustomViewIconTextViewBinding

class IconTextViewSmall @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomViewIconTextViewBinding
    private val iconResourceId: Int
    private val text: String?
    val textView: TextView
        get() = binding.textView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_view_icon_text_view, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconTextViewSmall, defStyleAttr, 0)

        iconResourceId = typedArray.getResourceId(R.styleable.IconTextViewSmall_iconTextViewSmallIcon, R.drawable.ic_location)
        binding.imageViewIc.setImageResource(iconResourceId)

        text = typedArray.getString(R.styleable.IconTextViewSmall_iconTextViewSmallText)

        text?.apply {
            binding.textView.text = text
        }

        typedArray.recycle()
    }
}