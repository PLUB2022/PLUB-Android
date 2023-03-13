package com.plub.presentation.ui.main.archive.bottomsheet.dots

import com.plub.domain.model.enums.ArchiveMenuType
import com.plub.presentation.ui.PageState

data class ArchiveDotsMenuBottomSheetState(
    val typeList : List<ArchiveMenuType> = emptyList()
) : PageState
