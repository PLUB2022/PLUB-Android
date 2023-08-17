package com.plub.presentation.ui.onboarding

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.plub.presentation.base.BaseActivity
import com.plub.presentation.databinding.ActivityOnboardingBinding
import com.plub.presentation.ui.sign.SignActivity
import com.plub.presentation.ui.onboarding.adapter.OnboardingViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding, OnboardingPageState, OnboardingViewModel>(
    ActivityOnboardingBinding::inflate) {

    override val viewModel: OnboardingViewModel by viewModels()
    private val pagerAdapter = OnboardingViewPagerAdapter()

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.onBackPressed(binding.viewPager.currentItem)
        }
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewPager.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
                dotsIndicator.attachTo(this)
            }
        }

        onBackPressedDispatcher.addCallback(this, backPressedDispatcher)
        viewModel.fetchOnboardingData()
    }

    override fun initState() {
        super.initState()

        repeatOnStarted {
            launch {
                viewModel.uiState.collect {
                    pagerAdapter.submitList(it.onboardingDataList) {
                        movePage(it.currentPage)
                    }
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as OnboardingEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: OnboardingEvent) {
        when(event) {
            is OnboardingEvent.GoToLoginFragment -> goToLogin()
            is OnboardingEvent.NavigationPopEvent -> finish()
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, SignActivity::class.java))
        finish()
    }

    private fun movePage(page:Int) {
        binding.viewPager.currentItem = page
    }
}