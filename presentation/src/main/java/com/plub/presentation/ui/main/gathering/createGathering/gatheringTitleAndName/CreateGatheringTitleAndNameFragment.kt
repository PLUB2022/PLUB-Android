package com.plub.presentation.ui.main.gathering.createGathering.gatheringTitleAndName

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringTitleAndNameBinding
import com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent
import com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringTitleAndNameFragment :
    BaseFragment<FragmentCreateGatheringTitleAndNameBinding, CreateGatheringTitleAndNamePageState, CreateGatheringTitleAndNameViewModel>(
        FragmentCreateGatheringTitleAndNameBinding::inflate
    ) {

    override val viewModel: CreateGatheringTitleAndNameViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                parentViewModel.childrenPageStateFlow.collect {
                    viewModel.initUiState(it)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                    when (it) {
                        is com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent.GoToPrevPage -> {
                            parentViewModel.setChildrenPageState(viewModel.uiState.value)
                            parentViewModel.goToPrevPageAndEmitChildrenPageState()
                        }
                    }
                }
            }
        }
    }
}