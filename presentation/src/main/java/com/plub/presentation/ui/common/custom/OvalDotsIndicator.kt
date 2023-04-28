package com.plub.presentation.ui.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.plub.presentation.R
import com.plub.presentation.databinding.CustomViewOvalDotsIndicatorBinding

class OvalDotsIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomViewOvalDotsIndicatorBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_view_oval_dots_indicator, this, true)
    }

    fun attachTo(viewpager2: ViewPager2) {
        binding.dotsIndicator.attachTo(viewpager2)
        binding.dotsIndicator2.attachTo(viewpager2)
    }

    fun <T> setSingleItemIndicatorSize(items: List<T>) {
        binding.dotsIndicator2.selectedDotColor = if(items.size == 1) context.getColor(R.color.color_5f5ff9) else context.getColor(R.color.transparent)
    }
}