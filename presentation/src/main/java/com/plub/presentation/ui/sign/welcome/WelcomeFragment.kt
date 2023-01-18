package com.plub.presentation.ui.sign.welcome

import android.content.Intent
import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentWelcomeBinding
import com.plub.presentation.event.WelcomeEvent
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.home.MainActivity
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
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as WelcomeEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.GoToMain -> {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}