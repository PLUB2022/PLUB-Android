package com.plub.presentation.ui.main.plubing.board.write

import android.text.SpannableString
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.WriteBoardFeedTypeVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

data class BoardWritePageState(
    val feedTypeList: StateFlow<List<WriteBoardFeedTypeVo>>,
    val selectedFeedType: StateFlow<PlubingFeedType>,
    val imageFile: StateFlow<File?>,
    val plubingName: StateFlow<String>,
    val title: MutableStateFlow<String>,
    val content: MutableStateFlow<String>,
    val contentCount: StateFlow<SpannableString>,
    val contentMaxLength: StateFlow<Int>,
    val isPostButtonEnable: StateFlow<Boolean>,
    val editImageUrl: StateFlow<String>,
) : PageState