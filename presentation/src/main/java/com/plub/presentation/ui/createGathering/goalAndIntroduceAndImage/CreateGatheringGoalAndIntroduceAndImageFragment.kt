package com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.plub.domain.model.state.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringGoalAndIntroduceAndImageBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringGoalAndIntroduceAndImageFragment :
    BaseFragment<
            FragmentCreateGatheringGoalAndIntroduceAndImageBinding,
            CreateGatheringGoalAndIntroduceAndPicturePageState,
            CreateGatheringGoalAndIntroduceAndImageViewModel>
        (FragmentCreateGatheringGoalAndIntroduceAndImageBinding::inflate) {

    override val viewModel: CreateGatheringGoalAndIntroduceAndImageViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        with(binding) {
            vm = viewModel
            parentVm = parentViewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                if(viewModel.uiState.value != CreateGatheringGoalAndIntroduceAndPicturePageState())
                    return@launch

                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    if (pageState is CreateGatheringGoalAndIntroduceAndPicturePageState)
                        viewModel.initUiState(pageState)
                }
            }

            launch {
                viewModel.getImageFromGallery.collect {
                    PermissionManager.createGetImagePermission {
                        getImageFromGallery()
                    }
                }
            }
        }
    }

    private fun getImageFromGallery() {
        val intent = IntentUtil.getSingleImageIntent()
        gatheringImageResult.launch(intent)
    }

    private val gatheringImageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.proceedActivityResult(it)
    }
}