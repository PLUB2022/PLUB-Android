package com.plub.presentation.ui.bindingAdapter

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.R
import com.plub.presentation.ui.custom.IconTextView

@BindingAdapter("iconTextViewTextLocation")
fun IconTextView.bindIconTextViewTextLocation(data: KakaoLocationInfoDocumentVo?) {
    if(data == null)
        textView.text =
            context.getString(R.string.create_gathering_day_and_on_offline_and_location_gathering_location_hint)
    else
        textView.text = data.placeName
}
