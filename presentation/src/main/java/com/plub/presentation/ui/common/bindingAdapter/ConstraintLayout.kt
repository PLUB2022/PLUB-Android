package com.plub.presentation.ui.common.bindingAdapter

import android.graphics.drawable.Drawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.plub.domain.model.enums.OnOfflineType
import com.plub.presentation.ui.common.custom.IconEditText

@BindingAdapter("onOffline")
fun ConstraintLayout.setCreateGatheringLocationVisible(
    onOffline: String
) {
    visibility = if(onOffline == OnOfflineType.OFF.value) View.VISIBLE else View.GONE
}