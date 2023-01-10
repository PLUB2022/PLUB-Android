package com.plub.presentation.ui.bindingAdapter

import androidx.databinding.BindingAdapter
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.ui.custom.IconTextViewMedium
import com.plub.presentation.ui.custom.IconTextViewSmall

@BindingAdapter("iconTextViewMediumTextTime")
fun IconTextViewMedium.bindIconTextViewMediumTextTime(text: String) {
    if(text.isEmpty()) {
        textView.text =
            context.getString(R.string.create_gathering_day_and_on_offline_and_location_gathering_time_hint)
        setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
    }
    else {
        textView.text = text
        setBackgroundResource(R.drawable.bg_rectangle_empty_5f5ff9_radius_8)
    }
}
