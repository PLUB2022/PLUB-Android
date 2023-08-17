package com.plub.presentation.ui.splash

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentSplashBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.MainViewModel
import com.plub.presentation.ui.onboarding.OnboardingActivity
import com.plub.presentation.ui.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment :
BaseTestFragment<FragmentSplashBinding, PageState.Default, SplashViewModel>(FragmentSplashBinding::inflate){

    companion object{
        const val FIRST_APP = "isFirst"
    }

    override val viewModel: SplashViewModel by viewModels()
    private val parentViewModel by activityViewModels<MainViewModel>()

    override fun initView() {
        val pref: SharedPreferences = requireActivity().getSharedPreferences(FIRST_APP, Activity.MODE_PRIVATE)
        viewModel.fetchMyInfo(pref)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect { event ->
                    when(event) {
                        is SplashEvent.GoToMain -> {
                            parentViewModel.emitProcessIntent(requireActivity().intent)
                            val action = SplashFragmentDirections.actionSplashToMain()
                            findNavController().navigate(action)
                        }

                        is SplashEvent.GoToSignUp -> {
                            startActivity(Intent(requireActivity(), SignActivity::class.java))
                            requireActivity().finish()
                        }

                        is SplashEvent.GoToOnBoarding -> {
                            startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
    }
}