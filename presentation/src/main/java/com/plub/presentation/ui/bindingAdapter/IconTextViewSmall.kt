package com.plub.presentation.ui.bindingAdapter

import androidx.databinding.BindingAdapter
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.ui.custom.IconTextViewSmall

@BindingAdapter("iconTextViewSmallTextLocation")
fun IconTextViewSmall.bindIconTextViewSmallTextLocation(data: KakaoLocationInfoDocumentVo?) {
    if(data == null)
        textView.text =
            context.getString(R.string.create_gathering_day_and_on_offline_and_location_gathering_location_hint)
    else
        textView.text = data.placeName
}
