package com.plub.presentation.ui.sign.moreInfo

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MoreInfoViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<MoreInfoPageState>(MoreInfoPageState()) {

    init {
        onIntroChangedAfter()
    }

    fun onClickNextButton() {
        emitEventFlow(MoreInfoEvent.MoveToNext(uiState.value.moreInfoVo))
    }

    fun onIntroChangedAfter() {
        val introduce: String = uiState.value.moreInfoVo.introduce
        updateIntroduceState(introduce)
    }

    fun onSaveNickname(nickname:String) {
        val titleSpannableString = getTitleSpannableString(nickname)
        updateUiState { ui ->
            ui.copy(
                titleText = titleSpannableString
            )
        }
    }

    fun onInitMoreInfo(moreInfoVo: MoreInfoVo) {
        if(uiState.value != MoreInfoPageState()) return

        updateIntroduceState(moreInfoVo.introduce)
    }

    private fun updateIntroduceState(introduce:String) {
        updateUiState { uiState ->
            uiState.copy(
                isNextButtonEnable = isNextButtonEnable(introduce),
                introduceCount = getIntroduceCountSpannableString(introduce.length.toString())
            )
        }
    }

    private fun getTitleSpannableString(nickname: String):SpannableString {
        val titleString = resourceProvider.getString(R.string.sign_up_more_info_title,nickname)
        val nicknameColor = resourceProvider.getColor(R.color.color_5f5ff9)
        return SpannableString(titleString).apply {
            setSpan(ForegroundColorSpan(nicknameColor),0,nickname.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun getIntroduceCountSpannableString(introduceLength: String):SpannableString {
        val introduceCountString = resourceProvider.getString(R.string.sign_up_more_info_introduce_count,introduceLength)
        val introduceCountColor = resourceProvider.getColor(R.color.color_363636)
        return SpannableString(introduceCountString).apply {
            setSpan(ForegroundColorSpan(introduceCountColor),0,introduceLength.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun isNextButtonEnable(introduce: String): Boolean {
        return introduce.isNotEmpty()
    }
}