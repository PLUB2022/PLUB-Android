package com.plub.data.mapper.todo

import com.plub.data.base.Mapper
import com.plub.data.dto.todo.TodoWriteRequest
import com.plub.domain.model.vo.todo.TodoWriteRequestVo

object TodoWriteRequestMapper : Mapper.RequestMapper<TodoWriteRequest, TodoWriteRequestVo> {
    override fun mapModelToDto(type: TodoWriteRequestVo): TodoWriteRequest {
        return type.run {
            TodoWriteRequest(
                content = content,
                date = date
            )
        }
    }
}