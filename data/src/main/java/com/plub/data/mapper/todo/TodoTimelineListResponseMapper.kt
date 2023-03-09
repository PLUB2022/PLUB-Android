package com.plub.data.mapper.todo

import com.plub.data.base.Mapper
import com.plub.data.dto.todo.TodoTimelineListResponse
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.todo.TodoTimelineListVo

object TodoTimelineListResponseMapper : Mapper.ResponseMapper<TodoTimelineListResponse, TodoTimelineListVo> {
    private val todoTimelineRespoMapper = TodoTimelineResponseMapper

    override fun mapDtoToModel(type: TodoTimelineListResponse?): TodoTimelineListVo {
        return type?.run {
            TodoTimelineListVo(
                last = last,
                content = content.map {
                    todoTimelineRespoMapper.mapDtoToModel(it)
                }
            )
        } ?: TodoTimelineListVo()
    }

    fun setViewType(viewType: TodoItemViewType) {
        todoTimelineRespoMapper.setViewType(viewType)
    }
}