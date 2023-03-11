package com.plub.presentation.ui.main.archive

import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.ui.PageState

data class ArchivePageState(
    val title : String = "",
    val archiveList : List<ArchiveContentResponseVo> = emptyList(),
    val isLoading : Boolean = false
) : PageState
