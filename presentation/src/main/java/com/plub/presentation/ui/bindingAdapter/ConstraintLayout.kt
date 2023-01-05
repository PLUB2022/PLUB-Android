package com.plub.presentation.ui.bindingAdapter

import android.graphics.drawable.Drawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.plub.domain.model.enums.OnOfflineType
import com.plub.presentation.ui.custom.IconEditText

@BindingAdapter("onOffline")
fun ConstraintLayout.setCreateGatheringLocationVisible(
    onOffline: String
) {
    visibility = if(onOffline == OnOfflineType.OFF.value) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["isSearched","beforeSearchBackground", "afterSearchBackground"], requireAll = true)
fun ConstraintLayout.bindSetConstraintLayoutBackground(
    isSearched: Boolean,
    beforeSearchBackground: Drawable,
    afterSearchBackground: Drawable
) {
    background = if(isSearched) afterSearchBackground
    else beforeSearchBackground
}