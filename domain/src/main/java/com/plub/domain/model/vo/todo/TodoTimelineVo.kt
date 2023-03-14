package com.plub.domain.model.vo.todo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.enums.TodoTimelineViewType
import com.plub.domain.model.vo.account.AccountInfoVo

data class TodoTimelineVo(
    val viewType: TodoTimelineViewType = TodoTimelineViewType.PLUBING,
    val timelineId: Int = -1,
    val date: String = "",
    val totalLikes: Int = -1,
    val accountInfoVo: AccountInfoVo = AccountInfoVo(),
    val todoList: List<TodoItemVo> = emptyList(),
    val isAuthor:Boolean = false,
    val isLike:Boolean = false
) : DomainModel