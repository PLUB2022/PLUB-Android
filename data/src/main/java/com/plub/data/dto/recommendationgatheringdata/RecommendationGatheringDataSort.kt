package com.plub.data.dto.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringDataSort(
    val empty : Boolean,
    val sorted : Boolean,
    val unsorted : Boolean
) : DataDto