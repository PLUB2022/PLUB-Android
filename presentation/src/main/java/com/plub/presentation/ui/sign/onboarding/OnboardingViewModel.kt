package com.plub.presentation.ui.sign.onboarding

import com.plub.domain.model.vo.onboarding.OnboardingItemVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
) : BaseViewModel<OnboardingPageState>(OnboardingPageState()) {

    private var maxPage:Int? = null

    fun onBackPressed(currentPage: Int) {
        val previousPage = currentPage - 1
        if (isFirstPage(currentPage)) goToNavUp() else moveToPage(previousPage)
    }

    fun fetchOnboardingData() {
        val onboardingList = generateOnboardingItemVoList()
        maxPage = onboardingList.size - 1
        updateUiState { uiState ->
            uiState.copy(onboardingDataList = onboardingList)
        }
    }

    fun onClickSkip() {
        goToLogin()
    }

    fun onClickNext() {
        maxPage?.let {
            var currentPage = uiState.value.currentPage
            if(currentPage == it) goToLogin() else moveToPage(++currentPage)
        }
    }

    private fun goToLogin() {
        emitEventFlow(OnboardingEvent.GoToLoginFragment)
    }

    private fun generateOnboardingItemVoList(): List<OnboardingItemVo> {
        return listOf(
            OnboardingItemVo(
                R.raw.onboarding_dummy_first,
                getStringResource(R.string.onboarding_title_first),
                getStringResource(R.string.onboarding_content_first)
            ),
            OnboardingItemVo(
                R.raw.onboarding_dummy_second,
                getStringResource(R.string.onboarding_title_second),
                getStringResource(R.string.onboarding_content_second)
            ),
            OnboardingItemVo(
                R.raw.onboarding_dummy_third,
                getStringResource(R.string.onboarding_title_third),
                getStringResource(R.string.onboarding_content_third)
            ),
        )
    }

    private fun getStringResource(res: Int): String {
        return resourceProvider.getString(res)
    }

    private fun isFirstPage(currentPage: Int) = currentPage == 0

    private fun goToNavUp() {
        emitEventFlow(OnboardingEvent.NavigationPopEvent)
    }

    private fun moveToPage(page: Int) {
        updateUiState { uiState ->
            uiState.copy(currentPage = page)
        }
    }
}