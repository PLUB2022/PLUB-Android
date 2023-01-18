package com.plub.presentation.ui.bindingAdapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.plub.presentation.R
import com.plub.presentation.util.px


@BindingAdapter("textViewPositionX")
fun TextView.bindTextViewPositionX(positionX: Float) {
    val display = this.resources?.displayMetrics
    val deviceWidth = display?.widthPixels ?: 0

    val newPositionX = positionX + 4.px

    when {
        newPositionX < 16.px -> this.x = 16.px.toFloat()
        newPositionX > deviceWidth - 74.px -> this.x = (deviceWidth - 74.px).toFloat()
        else -> this.x = newPositionX
    }
}

@BindingAdapter("searchResultEmptyTextStyle")
fun TextView.bindSearchResultEmptyTextStyle(searchText: String) {
    val description = context.getString(R.string.bottom_sheet_search_gathering_location_search_result_empty_title, searchText)
    val highlightStartIdx = 0
    val highlightEndIdx = searchText.length.inc().inc()

    val highlightedDescription = SpannableString(description)
    highlightedDescription.setSpan(
        TextAppearanceSpan(context, R.style.temp_search_result_empty_highlight_text),
        highlightStartIdx,
        highlightEndIdx,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    highlightedDescription.setSpan(
        TextAppearanceSpan(context, R.style.temp_search_result_empty_text),
        highlightEndIdx,
        highlightedDescription.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    setText(highlightedDescription, TextView.BufferType.SPANNABLE)
}