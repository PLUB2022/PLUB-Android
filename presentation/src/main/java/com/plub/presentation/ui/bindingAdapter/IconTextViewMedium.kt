package com.plub.presentation.ui.bindingAdapter

import androidx.databinding.BindingAdapter
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.ui.custom.IconTextViewMedium
import com.plub.presentation.ui.custom.IconTextViewSmall

@BindingAdapter("iconTextViewMediumTextAndIconAndBackgroundCreateGatheringTime")
fun IconTextViewMedium.bindIconTextViewMediumTextTime(text: String) {
    if(text.isEmpty()) {
        textView.text =
            context.getString(R.string.create_gathering_day_and_on_offline_and_location_gathering_time_hint)
        textView.setTextColor(context.getColor(R.color.color_8c8c8c))
        setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
        icon.setImageResource(R.drawable.ic_clock_inactive)
    }
    else {
        textView.text = text
        textView.setTextColor(context.getColor(R.color.color_363636))
        setBackgroundResource(R.drawable.bg_rectangle_empty_5f5ff9_radius_8)
        icon.setImageResource(R.drawable.ic_clock_active)
    }
}
