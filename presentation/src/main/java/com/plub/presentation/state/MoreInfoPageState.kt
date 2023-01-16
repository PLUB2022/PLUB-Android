package com.plub.presentation.state

import android.text.SpannableString
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo

data class MoreInfoPageState(
    val isNextButtonEnable:Boolean = false,
    val titleText:SpannableString = SpannableString(""),
    val introduceCount:SpannableString = SpannableString(""),
    val moreInfoVo:MoreInfoVo = MoreInfoVo()
): PageState