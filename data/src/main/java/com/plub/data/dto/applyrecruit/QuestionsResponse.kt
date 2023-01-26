package com.plub.data.dto.applyrecruit

import com.plub.data.base.DataDto

data class QuestionsResponse(
    val questions : List<Questions> = emptyList()
) : DataDto