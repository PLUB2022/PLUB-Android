package com.plub.presentation.ui.createGathering.question

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.domain.model.state.createGathering.CreateGatheringQuestionPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringQuestionBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringQuestionFragment : BaseFragment<
        FragmentCreateGatheringQuestionBinding, CreateGatheringQuestionPageState, CreateGatheringQuestionViewModel>(
    FragmentCreateGatheringQuestionBinding::inflate
) {
    override val viewModel: CreateGatheringQuestionViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        with(binding) {
            vm = viewModel
            parentVm = parentViewModel
        }
    }
}