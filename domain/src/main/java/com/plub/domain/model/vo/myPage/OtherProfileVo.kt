package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.OtherProfileBottomSheetViewType
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.todo.TodoTimelineListVo

data class OtherProfileVo(
    val info : MyInfoResponseVo,
    val todoList : TodoTimelineListVo,
    val viewType : OtherProfileBottomSheetViewType
) : DomainModel
