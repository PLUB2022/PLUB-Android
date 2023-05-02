package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.domain.model.vo.search.SearchPlubRecruitRequestVo

object SearchPlubRecruitRequestMapper : Mapper.RequestMapper<Map<String, String>, SearchPlubRecruitRequestVo> {
    private const val KEY_KEYWORD = "keyword"
    private const val KEY_TYPE = "type"
    private const val KEY_CURSOR_ID = "cursorId"
    private const val KEY_SORT = "sort"

    override fun mapModelToDto(type: SearchPlubRecruitRequestVo): Map<String, String> {
        return type.run {
            mapOf(
                KEY_KEYWORD to keyword,
                KEY_TYPE to this.type.key,
                KEY_CURSOR_ID to cursorId.toString(),
                KEY_SORT to sortType.key
            )
        }
    }
}