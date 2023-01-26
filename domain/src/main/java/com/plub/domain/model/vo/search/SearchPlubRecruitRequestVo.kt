package com.plub.domain.model.vo.search

import com.plub.domain.base.DomainModel
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.enums.PlubSortType

data class SearchPlubRecruitRequestVo(
    val type: PlubSearchType,
    val keyword: String,
    val sortType: PlubSortType,
    val page: Int,
) : DomainModel()
