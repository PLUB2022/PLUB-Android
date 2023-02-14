package com.plub.presentation.ui.main.archive.upload

import com.plub.domain.model.vo.archive.ArchiveUploadVo
import com.plub.presentation.ui.PageState

data class ArchiveUploadPageState(
    val imageCount : Int = 0,
    val archiveUploadVoList: List<ArchiveUploadVo> = emptyList()
) : PageState
