package com.plub.presentation.ui.main.archive.upload

import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class ArchiveUploadPageState(
    val title : StateFlow<String>,
    val typeTitle : StateFlow<String>,
    val enableButton : StateFlow<Boolean>,
    val imageCount : StateFlow<Int>,
    val pageType : StateFlow<Int>,
    val archiveUploadVoList: StateFlow<List<ArchiveUploadVo>>
) : PageState
