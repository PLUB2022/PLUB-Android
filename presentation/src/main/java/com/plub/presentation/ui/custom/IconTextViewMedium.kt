package com.plub.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.CustomViewIconTextViewMediumBinding

class IconTextViewMedium @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomViewIconTextViewMediumBinding
    private val iconResourceId: Int
    private val text: String?
    val textView: TextView
        get() = binding.textView

    val icon: ImageView
        get() = binding.imageViewIc

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_view_icon_text_view_medium, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconTextViewMedium, defStyleAttr, 0)

        iconResourceId = typedArray.getResourceId(R.styleable.IconTextViewMedium_iconTextViewMediumIcon, R.drawable.ic_location_inactive)
        binding.imageViewIc.setImageResource(iconResourceId)

        text = typedArray.getString(R.styleable.IconTextViewMedium_iconTextViewMediumText)

        text?.apply {
            binding.textView.text = text
        }

        typedArray.recycle()
    }
}