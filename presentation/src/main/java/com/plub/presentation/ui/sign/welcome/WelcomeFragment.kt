package com.plub.presentation.ui.sign.welcome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentWelcomeBinding
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.sign.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, PageState.Default, WelcomeViewModel>(
    FragmentWelcomeBinding::inflate
) {

    override val viewModel: WelcomeViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.goToMain.collect {

                }
            }
        }
    }
}