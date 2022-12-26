package com.plub.presentation.ui.createGathering.goalAndIntroduceAndPicture

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.plub.domain.model.state.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.domain.model.state.CreateGatheringTitleAndNamePageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringGoalAndIntroduceAndPictureBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringGoalAndIntroduceAndPictureFragment :
    BaseFragment<
            FragmentCreateGatheringGoalAndIntroduceAndPictureBinding,
            CreateGatheringGoalAndIntroduceAndPicturePageState,
            CreateGatheringGoalAndIntroduceAndPictureViewModel>
        (FragmentCreateGatheringGoalAndIntroduceAndPictureBinding::inflate) {

    override val viewModel: CreateGatheringGoalAndIntroduceAndPictureViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        with(binding) {
            vm = viewModel
            parentVm = parentViewModel
        }
    }

    override fun initState() {
        super.initState()

        repeatOnStarted(viewLifecycleOwner) {
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