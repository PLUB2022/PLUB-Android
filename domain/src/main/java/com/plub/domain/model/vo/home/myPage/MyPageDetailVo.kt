package com.plub.domain.model.vo.home.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.myPage.MyPageApplicationsVo

data class MyPageDetailVo(
    val viewType: MyPageDetailViewType = MyPageDetailViewType.EMPTY,
    val application : MyPageApplicationsVo = MyPageApplicationsVo(),
    val title : MyPageDetailTitleVo = MyPageDetailTitleVo()
) : DomainModel
