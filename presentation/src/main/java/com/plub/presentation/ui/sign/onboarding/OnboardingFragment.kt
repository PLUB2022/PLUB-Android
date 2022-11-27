package com.plub.presentation.ui.sign.onboarding

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.domain.model.state.OnboardingPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentOnboardingBinding
import com.plub.presentation.ui.sign.onboarding.adapter.OnboardingViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding,OnboardingPageState, OnboardingViewModel>(
    FragmentOnboardingBinding::inflate
) {

    override val viewModel: OnboardingViewModel by viewModels()
    private val pagerAdapter = OnboardingViewPagerAdapter()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewPager.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
                dotsIndicator.attachTo(this)
            }
        }
        viewModel.fetchOnboardingData()
    }

    override fun initState() {
        super.initState()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    pagerAdapter.submitList(it.onboardingDataList) {
                        movePage(it.currentPage)
                    }
                }
            }
            launch {
                viewModel.goToLoginFragment.collect {
                    goToLogin()
                }
            }
        }
    }

    private fun goToLogin() {
        val action = OnboardingFragmentDirections.actionOnboardingToLogin()
        findNavController().navigate(action)
    }

    private fun movePage(page:Int) {
        binding.viewPager.currentItem = page
    }
}