package com.plub.presentation.ui.common.bindingAdapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.plub.domain.model.enums.CreateGatheringPageType
import com.plub.presentation.ui.common.custom.OvalDotsIndicator

@BindingAdapter("createGatheringCurrentPage")
fun OvalDotsIndicator.setPage(
    createGatheringCurrentPage: Int
) {
    visibility = if(createGatheringCurrentPage < CreateGatheringPageType.PREVIEW.idx) View.VISIBLE else View.GONE
}