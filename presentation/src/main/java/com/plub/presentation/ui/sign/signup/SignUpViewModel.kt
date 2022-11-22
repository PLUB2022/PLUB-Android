package com.plub.presentation.ui.sign.signup

import android.text.SpannableString
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.sign.terms.TermsFragment
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PageState.Default>(PageState.Default) {

    fun onClickNext() {

    }
}