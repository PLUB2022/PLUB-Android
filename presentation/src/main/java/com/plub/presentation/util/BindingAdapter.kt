package com.plub.presentation.util

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.gms.common.SignInButton
import com.plub.domain.UiState
import com.plub.domain.successOrNull

@BindingAdapter("showLoadingProgressBar")
fun ProgressBar.bindShow(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("showErrorPage")
fun View.bindShowErrorPage(uiState: UiState<*>) {
    visibility = if (uiState is UiState.Error) View.VISIBLE else View.GONE
}

@BindingAdapter("android:onClick")
fun SignInButton.bindSignInClick(method: () -> Unit) {
    setOnClickListener { method.invoke() }
}