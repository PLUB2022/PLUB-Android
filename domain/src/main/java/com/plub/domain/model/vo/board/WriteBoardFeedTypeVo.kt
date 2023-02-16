package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingFeedType

data class WriteBoardFeedTypeVo(
    val feedType: PlubingFeedType = PlubingFeedType.TEXT,
    val isClicked:Boolean = false,
) : DomainModel