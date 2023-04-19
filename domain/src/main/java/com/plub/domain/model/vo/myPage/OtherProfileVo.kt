package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.OtherProfileBottomSheetViewType
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.todo.TodoTimelineVo

data class OtherProfileVo(
    val info : MyInfoResponseVo = MyInfoResponseVo(),
    val todoList : TodoTimelineVo = TodoTimelineVo(),
    val viewType : OtherProfileBottomSheetViewType = OtherProfileBottomSheetViewType.PROFILE
) : DomainModel
