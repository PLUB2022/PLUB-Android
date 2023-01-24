package com.plub.presentation.ui.createGathering.preview

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringPreviewBinding
import com.plub.presentation.ui.createGathering.CreateGatheringEvent
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringPreviewFragment : BaseFragment<
        FragmentCreateGatheringPreviewBinding, CreateGatheringPreviewPageState, CreateGatheringPreviewViewModel>(
    FragmentCreateGatheringPreviewBinding::inflate
) {
    override val viewModel: CreateGatheringPreviewViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }

        viewModel.fetchMyInfoUrl()
    }

    override fun onResume() {
        super.onResume()

        parentViewModel.updateChildrenPageState()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            parentViewModel.eventFlow.collect {
                if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                when (it) {
                    is CreateGatheringEvent.GoToPrevPage -> {
                        parentViewModel.goToPrevPageAndEmitChildrenPageState()
                    }
                }
            }
        }
    }
}