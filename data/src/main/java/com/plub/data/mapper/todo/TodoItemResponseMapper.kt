package com.plub.data.mapper.todo

import com.plub.data.base.Mapper
import com.plub.data.dto.board.BoardCommentListResponse
import com.plub.data.dto.todo.TodoItemResponse
import com.plub.data.dto.todo.TodoTimelineListResponse
import com.plub.data.dto.todo.TodoTimelineResponse
import com.plub.data.mapper.AccountInfoResponseMapper
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.board.BoardCommentListVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo
import com.plub.domain.model.vo.todo.TodoTimelineVo

object TodoItemResponseMapper : Mapper.ResponseMapper<TodoItemResponse, TodoItemVo> {
    private var viewType: TodoItemViewType? = null

    override fun mapDtoToModel(type: TodoItemResponse?): TodoItemVo {
        return type?.run {
            TodoItemVo(
                viewType = viewType ?: TodoItemViewType.PLUBING,
                todoId = todoId,
                content = content,
                date = date,
                isChecked = isChecked,
                isProof = isProof,
                proofImage = proofImage,
                likes = likes,
                isAuthor = isAuthor
            )
        } ?: TodoItemVo()
    }

    fun setViewType(viewType: TodoItemViewType) {
        this.viewType = viewType
    }
}