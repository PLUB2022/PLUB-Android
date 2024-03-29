package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageActiveDetailViewType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.todo.TodoTimelineVo

data class MyPageActiveDetailVo(
    val viewType: MyPageActiveDetailViewType = MyPageActiveDetailViewType.TOP,
    val todoList : List<TodoTimelineVo> = emptyList(),
    val postList:List<PlubingBoardVo> = emptyList(),
    val title : MyPageDetailTitleVo = MyPageDetailTitleVo()
) : DomainModel