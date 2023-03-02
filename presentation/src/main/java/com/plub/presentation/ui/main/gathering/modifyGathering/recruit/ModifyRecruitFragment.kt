package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyRecruitBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndImageEvent
import com.plub.presentation.ui.main.gathering.createGathering.question.bottomSheet.BottomSheetDeleteQuestion
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyRecruitFragment : BaseFragment<
        FragmentModifyRecruitBinding, ModifyRecruitPageState, ModifyRecruitViewModel>(
    FragmentModifyRecruitBinding::inflate
) {
    override val viewModel: ModifyRecruitViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        val pageState = arguments?.parcelable(MODIFY_RECRUIT_PAGE_STATE) ?: ModifyRecruitPageState()
        viewModel.initPageState(pageState)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    when (it) {
                        is ModifyRecruitEvent.ShowSelectImageBottomSheetDialog -> {
                            PermissionManager.createGetImagePermission {
                                showBottomSheetDialogSelectImage()
                            }
                        }

                        is ModifyRecruitEvent.GetImageFromCamera -> {
                            getImageFromCamera(it.uri)
                        }

                        is ModifyRecruitEvent.GetImageFromGallery -> {
                            getImageFromGallery()
                        }

                        is ModifyRecruitEvent.CropImageAndOptimize -> {
                            startCropImage(it.cropImageContractOptions)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val MODIFY_RECRUIT_PAGE_STATE = "MODIFY_RECRUIT_PAGE_STATE"

        fun newInstance(
            initPageState: ModifyRecruitPageState
        ) = ModifyRecruitFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MODIFY_RECRUIT_PAGE_STATE, initPageState)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        arguments?.putParcelable(MODIFY_RECRUIT_PAGE_STATE, viewModel.uiState.value)
    }

    private fun getImageFromCamera(uri: Uri) {
        val intent = IntentUtil.getOpenCameraIntent(uri)
        gatheringImageFromCameraResult.launch(intent)
    }

    private val gatheringImageFromCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.proceedGatheringImageFromCameraResult(it)
        }

    private fun getImageFromGallery() {
        val intent = IntentUtil.getSingleImageIntent()
        gatheringImageFromGalleryResult.launch(intent)
    }

    private val gatheringImageFromGalleryResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.proceedGatheringImageFromGalleryResult(it)
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE_HAS_DEFAULT) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun startCropImage(option: CropImageContractOptions) {
        cropImage.launch(option)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }
}
