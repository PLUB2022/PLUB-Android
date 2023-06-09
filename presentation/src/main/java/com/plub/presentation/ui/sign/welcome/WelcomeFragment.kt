package com.plub.presentation.ui.sign.welcome

import android.content.Intent
import androidx.fragment.app.viewModels
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentWelcomeBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.MainActivity
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
            lottieWelcome.setAnimation(R.raw.welcome_sign_up)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as WelcomeEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: WelcomeEvent) {
        when(event) {
            is WelcomeEvent.GoToMain -> {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}