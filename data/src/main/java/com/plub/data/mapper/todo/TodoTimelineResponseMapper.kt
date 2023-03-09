package com.plub.data.mapper.todo

import com.plub.data.base.Mapper
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.todo.TodoTimelineListResponse
import com.plub.data.dto.todo.TodoTimelineResponse
import com.plub.data.mapper.AccountInfoResponseMapper
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo

object TodoTimelineResponseMapper : Mapper.ResponseMapper<TodoTimelineResponse, TodoTimelineVo> {
    private val todoItemRespoMapper = TodoItemResponseMapper

    override fun mapDtoToModel(type: TodoTimelineResponse?): TodoTimelineVo {
        return type?.run {
            TodoTimelineVo(
                timelineId = todoTimelineId,
                date = date,
                totalLikes = totalLikes,
                accountInfoVo = AccountInfoResponseMapper.mapDtoToModel(accountInfo),
                todoList = todoList.map {
                    todoItemRespoMapper.mapDtoToModel(it)
                }
            )
        } ?: TodoTimelineVo()
    }

    fun setViewType(viewType: TodoItemViewType) {
        todoItemRespoMapper.setViewType(viewType)
    }
}