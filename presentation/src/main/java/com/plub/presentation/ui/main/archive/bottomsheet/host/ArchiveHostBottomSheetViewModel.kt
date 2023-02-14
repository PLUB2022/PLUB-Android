package com.plub.presentation.ui.main.archive.bottomsheet.host

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArchiveHostBottomSheetViewModel @Inject constructor(
) : BaseViewModel<PageState.Default>(PageState.Default) {

}