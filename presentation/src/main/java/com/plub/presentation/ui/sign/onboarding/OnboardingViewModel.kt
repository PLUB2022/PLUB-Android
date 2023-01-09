package com.plub.presentation.ui.sign.onboarding

import androidx.lifecycle.viewModelScope
import com.plub.presentation.state.OnboardingPageState
import com.plub.domain.model.vo.onboarding.OnboardingItemVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
) : BaseViewModel<OnboardingPageState>(OnboardingPageState()) {

    companion object {
        private const val DEFAULT_PAGE = 0
    }

    private var currentPage = DEFAULT_PAGE
    private var maxPage:Int? = null
    private val _goToLoginFragment = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToLoginFragment: SharedFlow<Unit> = _goToLoginFragment.asSharedFlow()

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
            if(currentPage == it) goToLogin() else moveToNextPage()
        }
    }

    private fun goToLogin() {
        viewModelScope.launch {
            _goToLoginFragment.emit(Unit)
        }
    }

    private fun moveToNextPage() {
        updateUiState { uiState ->
            uiState.copy(currentPage = ++currentPage)
        }
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
}