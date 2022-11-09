package com.plub.presentation.ui.sign.login

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.plub.domain.model.state.OnboardingPageState
import com.plub.domain.model.state.PageState
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentLoginBinding
import com.plub.presentation.databinding.FragmentOnboardingBinding
import com.plub.presentation.ui.sign.onboarding.adapter.OnboardingViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,PageState.Default, LoginViewModel>(
    FragmentLoginBinding::inflate
) {

    override val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initState() {

    }
}