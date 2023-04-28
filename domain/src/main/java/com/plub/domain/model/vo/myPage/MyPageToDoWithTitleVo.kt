package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.vo.todo.TodoTimelineListVo

data class MyPageToDoWithTitleVo(
    val titleVo : MyPageDetailTitleVo = MyPageDetailTitleVo(),
    val todoTimelineListVo: TodoTimelineListVo = TodoTimelineListVo()
) : DomainModel
