package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageDetailViewType

data class MyPageDetailVo(
    val viewType: MyPageDetailViewType = MyPageDetailViewType.EMPTY,
    val application : MyPageApplicationsVo = MyPageApplicationsVo(),
    val title : MyPageDetailTitleVo = MyPageDetailTitleVo()
) : DomainModel
