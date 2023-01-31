package com.plub.presentation.ui.sign.moreInfo

import android.text.SpannableString
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.presentation.ui.PageState

data class MoreInfoPageState(
    val isNextButtonEnable:Boolean = false,
    val titleText:SpannableString = SpannableString(""),
    val introduceCount:SpannableString = SpannableString(""),
    val moreInfoVo:MoreInfoVo = MoreInfoVo()
): PageState