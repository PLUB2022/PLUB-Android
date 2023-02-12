package com.plub.presentation.ui.main.archive

import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.presentation.ui.Event

sealed class ArchiveEvent : Event{
    data class SeeDetailArchiveDialog(val responseVo: ArchiveDetailResponseVo) : ArchiveEvent()
}
