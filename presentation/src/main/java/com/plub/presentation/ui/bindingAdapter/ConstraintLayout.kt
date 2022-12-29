package com.plub.presentation.ui.bindingAdapter

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.plub.domain.model.enums.OnOfflineType

@BindingAdapter("onOffline")
fun ConstraintLayout.setCreateGatheringLocationVisible(
    onOffline: String
) {
    visibility = if(onOffline == OnOfflineType.OFF.value) View.VISIBLE else View.GONE
}