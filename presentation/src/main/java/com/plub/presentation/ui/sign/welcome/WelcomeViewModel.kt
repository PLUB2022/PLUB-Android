package com.plub.presentation.ui.sign.welcome

import androidx.lifecycle.viewModelScope
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PageState.Default>(PageState.Default) {

    private val _goToMain = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToMain: SharedFlow<Unit> = _goToMain.asSharedFlow()

    fun onClickPlubStartButton() {
        viewModelScope.launch {
            _goToMain.emit(Unit)
        }
    }
}