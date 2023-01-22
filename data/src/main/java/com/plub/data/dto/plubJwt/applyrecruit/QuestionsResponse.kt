package com.plub.data.dto.plubJwt.applyrecruit

import com.plub.data.base.DataDto

data class QuestionsResponse(
    val questions : List<Questions>
) : DataDto
