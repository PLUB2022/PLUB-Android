package com.plub.presentation.ui.sign.terms

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.state.TermsPageState
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.domain.model.vo.signUp.terms.TermsAgreementItemVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<TermsPageState>(TermsPageState()) {

    private val _moveToNextPage = MutableSharedFlow<TermsPageVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val moveToNextPage: SharedFlow<TermsPageVo> = _moveToNextPage.asSharedFlow()

    fun onClickNextButton() {
        viewModelScope.launch {
            val vo = mapToTermsPageVo(uiState.value.mapVo)
            _moveToNextPage.emit(vo)
        }
    }

    fun onClickTermsExpand(termsType: TermsType, isExpanded: Boolean) {
        val map = getChangeTermsExpandMap(termsType, isExpanded)
        updateUiState { ui ->
            ui.copy(mapVo = map)
        }
    }

    fun onClickTermsChecked(termsType: TermsType, isChecked: Boolean) {
        val map = getChangeTermsCheckMap(termsType, isChecked)
        updateUiState { ui ->
            ui.copy(mapVo = map, isNextButtonEnable = isNextButtonEnable(map))
        }
    }

    fun initTerms() {
        val map = getInitVoMap()
        updateUiState { ui ->
            ui.copy(mapVo = map)
        }
    }

    fun onInitTermsPageVo(termsPageVo: TermsPageVo) {
        updateUiState { ui ->
            val newMap = getInitTermsAgreementMap(termsPageVo)
            ui.copy(mapVo = newMap, isNextButtonEnable = isNextButtonEnable(newMap))
        }
    }

    private fun getChangeTermsExpandMap(termsType: TermsType, isExpanded: Boolean):Map<TermsType, TermsAgreementItemVo> {
        return uiState.value.mapVo.toMutableMap().mapValues {
            val expanded = if (it.key == termsType) !isExpanded else it.value.isExpanded
            it.value.copy(
                isExpanded = expanded
            )
        }
    }

    private fun getChangeTermsCheckMap(termsType: TermsType, isChecked: Boolean):Map<TermsType, TermsAgreementItemVo> {
        return when(termsType) {
            TermsType.ALL -> getAllCheckedMap(isChecked)
            else -> {
                val otherCheckedMap = getOtherCheckedMap(termsType, isChecked)
                getAllTypeCheckedMap(otherCheckedMap)
            }
        }
    }

    private fun getAllCheckedMap(isChecked: Boolean):Map<TermsType, TermsAgreementItemVo> {
        return uiState.value.mapVo.toMutableMap().mapValues {
            it.value.copy(isChecked = !isChecked)
        }
    }

    private fun getOtherCheckedMap(termsType: TermsType, isChecked: Boolean):Map<TermsType, TermsAgreementItemVo> {
        return uiState.value.mapVo.toMutableMap().mapValues {
            val check = if (it.key == termsType) !isChecked else it.value.isChecked
            it.value.copy(
                isChecked = check
            )
        }
    }

    private fun getAllTypeCheckedMap(map:Map<TermsType, TermsAgreementItemVo>):Map<TermsType, TermsAgreementItemVo> {
        val newMap = map.toMutableMap()
        newMap[TermsType.ALL]?.isChecked = isAllChecked(newMap)
        return newMap
    }

    private fun isAllChecked(map:Map<TermsType, TermsAgreementItemVo>): Boolean {
        return map.filterNot { it.key == TermsType.ALL }.all { it.value.isChecked }
    }

    private fun getInitVoMap():Map<TermsType, TermsAgreementItemVo> {
        val newMap = uiState.value.mapVo.toMutableMap()
        return newMap.mapValues {
            it.value.copy(
                title = getTermsTitleString(it.key),
                url = getTermsUrl(it.key)
            )
        }
    }

    private fun getTermsTitleString(type: TermsType): String {
        val titleRes = when (type) {
            TermsType.PRIVACY -> R.string.sign_up_terms_privacy_title
            TermsType.LOCATION -> R.string.sign_up_terms_location_title
            TermsType.AGE -> R.string.sign_up_terms_age_title
            TermsType.COLLECT -> R.string.sign_up_terms_collect_title
            TermsType.MARKETING -> R.string.sign_up_terms_marketing_title
            else -> R.string.sign_up_terms_privacy_title
        }
        return resourceProvider.getString(titleRes)
    }

    private fun getTermsUrl(type: TermsType): String {
        return "http://nextmatch.kr/privacy/youandi/agreement.html"
    }

    private fun isNextButtonEnable(map: Map<TermsType, TermsAgreementItemVo>):Boolean {
        return map.filter { isEssentialTerms(it.key) }.all { it.value.isChecked }
    }

    private fun isEssentialTerms(termsType: TermsType):Boolean {
        return when(termsType) {
            TermsType.PRIVACY,
            TermsType.LOCATION,
            TermsType.AGE,
            TermsType.COLLECT -> true
            else -> false
        }
    }

    private fun mapToTermsPageVo(map: Map<TermsType, TermsAgreementItemVo>):TermsPageVo {
        return TermsPageVo(
            map[TermsType.PRIVACY]?.isChecked ?: false,
            map[TermsType.LOCATION]?.isChecked ?: false,
            map[TermsType.AGE]?.isChecked ?: false,
            map[TermsType.COLLECT]?.isChecked ?: false,
            map[TermsType.MARKETING]?.isChecked ?: false,
        )
    }

    private fun getInitTermsAgreementMap(vo:TermsPageVo):Map<TermsType, TermsAgreementItemVo> {
        val map = uiState.value.mapVo.toMutableMap()
        map[TermsType.PRIVACY]?.isChecked = vo.privacy
        map[TermsType.LOCATION]?.isChecked = vo.location
        map[TermsType.AGE]?.isChecked = vo.age
        map[TermsType.COLLECT]?.isChecked = vo.collect
        map[TermsType.MARKETING]?.isChecked = vo.marketing
        return getAllTypeCheckedMap(map)
    }
}