package com.plub.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.CustomViewFixedTextWidthCheckBoxBinding
import com.plub.presentation.util.dp

class FixedTextWidthCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomViewFixedTextWidthCheckBoxBinding
    private val checkedBackgroundId: Int
    private val uncheckedBackgroundId: Int
    private val checkedTextColorId: Int
    private val uncheckedTextColorId: Int

    var isChecked: Boolean = false
        set(value) {
            field = value

            setBackgroundResourceAndTextColor()
        }

    var onCheckBoxClick: (() -> Unit)? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_view_fixed_text_width_check_box, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedTextWidthCheckBox, defStyleAttr, 0)

        checkedBackgroundId = typedArray.getResourceId(R.styleable.FixedTextWidthCheckBox_checkedBackground, R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
        uncheckedBackgroundId = typedArray.getResourceId(R.styleable.FixedTextWidthCheckBox_uncheckedBackground,R.drawable.bg_rectangle_empty_8c8c8c_radius_8)

        checkedTextColorId = typedArray.getResourceId(R.styleable.FixedTextWidthCheckBox_checkedTextColor, R.color.color_363636)
        uncheckedTextColorId = typedArray.getResourceId(R.styleable.FixedTextWidthCheckBox_uncheckedTextColor, R.color.white)

        val paddingVertical = typedArray.getInt(R.styleable.FixedTextWidthCheckBox_paddingVertical, 0)
        val paddingHorizontal = typedArray.getInt(R.styleable.FixedTextWidthCheckBox_paddingHorizontal, 0)

        val textWidth = typedArray.getInt(R.styleable.FixedTextWidthCheckBox_textWidth, 0)
        val text = typedArray.getString(R.styleable.FixedTextWidthCheckBox_text)

        binding.constraintLayoutCheckBox.apply {
            setPadding(
                paddingHorizontal.dp,
                paddingVertical.dp,
                paddingHorizontal.dp,
                paddingVertical.dp)

            setOnClickListener {
                isChecked = !isChecked
                onCheckBoxClick?.let { it() }
            }
        }

        binding.textViewContent.apply {
            width = textWidth.dp
            this.text = text
        }

        setBackgroundResourceAndTextColor()
        typedArray.recycle()
    }

    private fun setBackgroundResourceAndTextColor() {
        if (isChecked) {
            binding.constraintLayoutCheckBox.setBackgroundResource(checkedBackgroundId)
            binding.textViewContent.setTextColor(
                ContextCompat.getColor(
                    context,
                    checkedTextColorId
                )
            )
        } else {
            binding.constraintLayoutCheckBox.setBackgroundResource(uncheckedBackgroundId)
            binding.textViewContent.setTextColor(
                ContextCompat.getColor(
                    context,
                    uncheckedTextColorId
                )
            )
        }
    }
}