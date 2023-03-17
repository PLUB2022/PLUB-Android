package com.plub.data.mapper.todo

import com.plub.data.base.Mapper
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.todo.GetTodoDaysResponse
import com.plub.data.dto.todo.TodoItemResponse
import com.plub.data.dto.todo.TodoTimelineListResponse
import com.plub.data.dto.todo.TodoTimelineResponse
import com.plub.data.mapper.AccountInfoResponseMapper
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo

object GetTodoDaysResponseMapper : Mapper.ResponseMapper<GetTodoDaysResponse, List<Int>> {
    override fun mapDtoToModel(type: GetTodoDaysResponse?): List<Int> {
        return type?.dateList?.map {
            it.toInt()
        }?: emptyList()
    }
}