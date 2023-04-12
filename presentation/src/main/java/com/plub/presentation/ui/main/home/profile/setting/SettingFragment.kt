package com.plub.presentation.ui.main.home.profile.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSettingBinding
import com.plub.presentation.ui.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment :
    BaseFragment<FragmentSettingBinding, PageState.Default, SettingViewModel>(
        FragmentSettingBinding::inflate
    ) {

    override val viewModel: SettingViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {

                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as SettingEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: SettingEvent) {
        when(event){
            is SettingEvent.GoToBack -> {findNavController().popBackStack()}
        }
    }
}