package com.plub.data.model.recommendationgatheringdata

import com.plub.data.base.DataDto

data class RecommendationGatheringDataSort(
    val empty : Boolean,
    val sorted : Boolean,
    val unsorted : Boolean
) : DataDto