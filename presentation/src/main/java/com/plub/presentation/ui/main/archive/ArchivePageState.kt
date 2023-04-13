package com.plub.presentation.ui.main.archive

import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class ArchivePageState(
    val title : StateFlow<String>,
    val archiveList : StateFlow<List<ArchiveContentResponseVo>>,
) : PageState
