package com.plub.domain.model.state.createGathering

import com.plub.domain.model.state.PageState

data class CreateGatheringPageState(
    val currentPage: Int = 0
) : PageState