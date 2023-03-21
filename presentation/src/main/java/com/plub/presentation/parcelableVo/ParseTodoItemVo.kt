package com.plub.presentation.parcelableVo

import android.os.Parcelable
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.todo.TodoItemVo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParseTodoItemVo(
    val viewType: TodoItemViewType = TodoItemViewType.PLUBING,
    val todoId: Int = -1,
    val content: String = "",
    val date: String = "",
    val isChecked: Boolean = false,
    val isProof: Boolean = false,
    val proofImage: String = "",
    val likes: Int = -1,
    val isAuthor: Boolean = false
) : Parcelable {

    companion object : ParseModel<ParseTodoItemVo, TodoItemVo> {
        override fun mapToParse(vo: TodoItemVo): ParseTodoItemVo {
            return vo.run {
                ParseTodoItemVo(
                    viewType, todoId, content, date, isChecked, isProof, proofImage, likes, isAuthor
                )
            }
        }

        override fun mapToDomain(vo: ParseTodoItemVo): TodoItemVo {
            return vo.run {
                TodoItemVo(
                    viewType, todoId, content, date, isChecked, isProof, proofImage, likes, isAuthor
                )
            }
        }
    }
}