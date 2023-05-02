package com.plub.presentation.ui.main.plubing.notice.write

import android.text.SpannableString
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class NoticeWritePageState(
    val plubingName:StateFlow<String>,
    val title: MutableStateFlow<String>,
    val content: MutableStateFlow<String>,
    val contentCount: StateFlow<SpannableString>,
    val isPostButtonEnable: StateFlow<Boolean>,
    val contentMaxLength: StateFlow<Int>
): PageState