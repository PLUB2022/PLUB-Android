package com.plub.presentation.state

import com.plub.domain.model.enums.GatheringShapeType

data class CategoryChoiceState(
    val listOrGrid : GatheringShapeType = GatheringShapeType.LIST
) : PageState