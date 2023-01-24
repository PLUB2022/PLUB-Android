package com.plub.presentation.ui.bindingAdapter

import android.graphics.Typeface
import androidx.annotation.Dimension
import androidx.databinding.BindingAdapter
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.ui.custom.IconTextViewSmall
import com.plub.presentation.util.dp
import com.plub.presentation.util.px

@BindingAdapter("iconTextViewSmallTextAndIconAndBackgroundCreateGatheringLocation")
fun IconTextViewSmall.bindIconTextViewSmallTextLocation(data: KakaoLocationInfoDocumentVo?) {
    if (data == null) {
        textView.text =
            context.getString(R.string.create_gathering_day_and_on_offline_and_location_gathering_location_hint)
        textView.setTextColor(context.getColor(R.color.color_8c8c8c))
        setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
        icon.setImageResource(R.drawable.ic_location_inactive)
    } else {
        textView.text = data.placeName
        textView.setTextColor(context.getColor(R.color.color_363636))
        textView.setTextSize(Dimension.SP, 16f)
        textView.typeface = Typeface.DEFAULT_BOLD
        setBackgroundResource(R.drawable.bg_rectangle_empty_5f5ff9_radius_8)
        icon.setImageResource(R.drawable.ic_location_active)
    }
}
