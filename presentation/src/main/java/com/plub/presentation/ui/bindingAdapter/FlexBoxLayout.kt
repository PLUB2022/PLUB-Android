package com.plub.presentation.ui.bindingAdapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.R
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.px

@BindingAdapter("categoryList")
fun FlexboxLayout.setCategoryList(items: List<SelectedHobbyVo>) {
    if (this.size != 0)
        return
    for (item in items) {
        val textView = TextView(context)
        textView.text = item.name

        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            20.px
        )
        lp.setMargins(0, 0, 8.px, 8.px)
        lp.gravity = Gravity.TOP
        textView.layoutParams = lp

        textView.setTextAppearance(R.style.category_text)
        textView.setTextColor(ContextCompat.getColor(context, R.color.color_5f5ff9))
        textView.setBackgroundResource(R.drawable.bg_rectangle_filled_e1e1fa_radius_4)
        textView.setPadding(8.px, 2.px, 8.px, 2.px)
        textView.includeFontPadding = false

        addView(textView)
    }
}