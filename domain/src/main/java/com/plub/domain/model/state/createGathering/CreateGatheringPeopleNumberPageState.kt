package com.plub.domain.model.state.createGathering

import com.plub.domain.model.state.PageState

data class CreateGatheringPeopleNumberPageState(
    val peopleNumber: Int = 0
) : PageState