package com.plub.presentation.ui.splash

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentSplashBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.MainActivity
import com.plub.presentation.ui.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment :
BaseTestFragment<FragmentSplashBinding, PageState.Default, SplashViewModel>(FragmentSplashBinding::inflate){

    override val viewModel: SplashViewModel by viewModels()

    override fun initView() {
        viewModel.fetchMyInfo()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect { event ->
                    when(event) {
                        is SplashEvent.GoToMain -> {
                            val action = SplashFragmentDirections.actionSplashToMain()
                            findNavController().navigate(action)
                        }

                        is SplashEvent.GoToSignUp -> {
                            startActivity(Intent(requireActivity(), SignActivity::class.java))
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
    }
}