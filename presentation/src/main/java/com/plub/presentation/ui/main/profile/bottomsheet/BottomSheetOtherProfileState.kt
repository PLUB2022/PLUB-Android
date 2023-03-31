package com.plub.presentation.ui.main.profile.bottomsheet

import com.plub.domain.model.vo.myPage.OtherProfileVo
import com.plub.presentation.ui.PageState

data class BottomSheetOtherProfileState(
    val dataList : List<OtherProfileVo>
) : PageState