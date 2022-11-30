package com.plub.presentation.ui.bindingAdapter

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter("currentPage")
fun ViewPager2.setPage(
    currentPage: Int
) {
    setCurrentItem(currentPage, true)
}