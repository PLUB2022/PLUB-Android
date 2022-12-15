package com.plub.presentation.ui.createGathering.goalAndIntroduceAndPicture

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringGoalAndIntroduceAndPictureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringGoalAndIntroduceAndPictureFragment : BaseFragment<FragmentCreateGatheringGoalAndIntroduceAndPictureBinding, PageState.Default, CreateGatheringGoalAndIntroduceAndPictureViewModel>(
    FragmentCreateGatheringGoalAndIntroduceAndPictureBinding::inflate
) {
    override val viewModel: CreateGatheringGoalAndIntroduceAndPictureViewModel by viewModels()

    override fun initView() {

    }
}