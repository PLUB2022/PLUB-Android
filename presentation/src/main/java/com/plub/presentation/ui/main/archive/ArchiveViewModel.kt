package com.plub.presentation.ui.main.archive

import com.plub.domain.usecase.GetAllArchiveUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    val getAllArchiveUseCase: GetAllArchiveUseCase
)  :BaseViewModel<PageState.Default>(PageState.Default) {

}