package com.plub.presentation.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.plub.presentation.R
import com.plub.presentation.databinding.CustomViewFixedTextWidthCheckBoxBinding
import com.plub.presentation.util.px

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

    /**
     * https://stackoverflow.com/questions/60005152/cannot-use-same-bindingadapter-on-two-different-views
     * Unit으로 할 경우 DataBinding Complier가 올바르지 않은 java code를 생성함.
     * 따라서 Void를 사용
     */
    var checkBoxClickEvent: (() -> Void)? = null

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
                paddingHorizontal.px,
                paddingVertical.px,
                paddingHorizontal.px,
                paddingVertical.px)
            setOnClickListener {
                isChecked = !isChecked
                checkBoxClickEvent?.let {
                    it()
                }
            }
        }

        binding.textViewContent.apply {
            width = textWidth.px
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