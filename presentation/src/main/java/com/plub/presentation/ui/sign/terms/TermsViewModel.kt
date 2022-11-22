package com.plub.presentation.ui.sign.terms

import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.state.TermsPageState
import com.plub.domain.model.vo.terms.TermsAgreementItemVo
import com.plub.domain.usecase.FetchTermsAgreementUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val fetchTermsAgreementUseCase: FetchTermsAgreementUseCase
) : BaseViewModel<TermsPageState>(TermsPageState()) {

    fun onClickTermsExpand(termsType: TermsType, isExpanded: Boolean) {
        val map = getChangeTermsExpandMap(termsType, isExpanded)
        updateMapVo(map)
    }

    fun onClickTermsChecked(termsType: TermsType, isChecked: Boolean) {
        val map = getChangeTermsCheckMap(termsType, isChecked)
        updateMapVo(map)
    }

    fun initTerms() {
        val map = getInitVoMap()
        updateMapVo(map)
    }

    private fun getChangeTermsExpandMap(termsType: TermsType, isExpanded: Boolean):Map<TermsType, TermsAgreementItemVo> {
        val newMap = uiState.value.mapVo.toMutableMap()
        return newMap.mapValues {
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
        val newMap = uiState.value.mapVo.toMutableMap()
        return newMap.mapValues {
            it.value.copy(isChecked = !isChecked)
        }
    }

    private fun getOtherCheckedMap(termsType: TermsType, isChecked: Boolean):Map<TermsType, TermsAgreementItemVo> {
        val newMap = uiState.value.mapVo.toMutableMap()
        return newMap.mapValues {
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

    private fun updateMapVo(map: Map<TermsType, TermsAgreementItemVo>) {
        updateUiState { ui ->
            ui.copy(mapVo = map)
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
}