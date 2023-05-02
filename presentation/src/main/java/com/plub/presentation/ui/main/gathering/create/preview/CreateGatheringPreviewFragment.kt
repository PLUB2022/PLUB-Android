package com.plub.presentation.ui.main.gathering.create.preview

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringPreviewBinding
import com.plub.presentation.ui.main.gathering.create.CreateGatheringViewModel
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

        viewModel.updateMyInfoUrl()
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
                    is com.plub.presentation.ui.main.gathering.create.CreateGatheringEvent.GoToPrevPage -> {
                        parentViewModel.goToPrevPageAndEmitChildrenPageState()
                    }
                }
            }
        }
    }
}