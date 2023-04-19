package com.plub.presentation.ui.main.profile.edit

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyPageEditBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.util.IntentUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MyPageEditFragment :
    BaseTestFragment<FragmentMyPageEditBinding, MyPageEditState, MyPageEditViewModel>(
        FragmentMyPageEditBinding::inflate) {

    @Inject
    lateinit var commonDialog: CommonDialog

    private lateinit var albumLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    override val viewModel: MyPageEditViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
        viewModel.onInitProfile()
        setAlbumLauncher()
        setCameraLauncher()
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE_HAS_DEFAULT) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun setAlbumLauncher() {
        albumLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
                result.data?.data?.let {
                    viewModel.onSelectImageFromAlbum(it)
                }
            }
    }

    private fun setCameraLauncher() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
                viewModel.onTakeImageFromCamera()
            }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }

    private fun startCropImage(option: CropImageContractOptions) {
        cropImage.launch(option)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.nicknameIsActive.collect {
                    nicknameIsActiveState(it)
                }
            }

            launch {
                viewModel.uiState.nickname.collect{
                    viewModel.updateButtonState()
                }
            }

            launch {
                viewModel.uiState.introduce.collect{
                    viewModel.updateButtonState()
                }
            }

            launch {
                viewModel.uiState.profileImage.collect{
                    viewModel.updateButtonState()
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as MyPageEditEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: MyPageEditEvent) {
        when (event) {
            is MyPageEditEvent.GoToAlbum -> {
                val intent = IntentUtil.getSingleImageIntent()
                albumLauncher.launch(intent)
            }
            is MyPageEditEvent.GoToCamera -> {
                val intent = IntentUtil.getOpenCameraIntent(event.uri)
                cameraLauncher.launch(intent)
            }
            is MyPageEditEvent.ShowSelectImageBottomSheetDialog -> showBottomSheetDialogSelectImage()
            is MyPageEditEvent.CropImageAndOptimize -> startCropImage(event.cropImageContractOptions)
            is MyPageEditEvent.ShowDialog -> {
                showConfirmDialog()
            }
            is MyPageEditEvent.GoToBack -> findNavController().popBackStack()
        }
    }

    private fun nicknameIsActiveState(isNicknameActive: Boolean?) {
        binding.apply {
            when (isNicknameActive) {
                true -> {
                    val textColor = ContextCompat.getColor(requireContext(), R.color.color_363636)
                    val color = ContextCompat.getColor(requireContext(), R.color.color_5f5ff9)
                    constraintLayoutNickname.setBackgroundResource(R.drawable.bg_rectangle_empty_5f5ff9_radius_8)
                    editTextNickname.setTextColor(textColor)
                    textViewNicknameDescription.setTextColor(color)
                    imageViewNicknameDescription.setImageResource(R.drawable.ic_check_circle_5f5ff9)
                    imageViewNicknameDelete.setImageResource(R.drawable.ic_delete_box_5f5ff9)
                }
                false -> {
                    val color = ContextCompat.getColor(requireContext(), R.color.color_f75b2b)
                    constraintLayoutNickname.setBackgroundResource(R.drawable.bg_rectangle_empty_f75b2b_radius_8)
                    editTextNickname.setTextColor(color)
                    textViewNicknameDescription.setTextColor(color)
                    imageViewNicknameDescription.setImageResource(R.drawable.ic_tooltip_f75b2b)
                    imageViewNicknameDelete.setImageResource(R.drawable.ic_delete_box_f75b2b)
                }
                null -> {
                    val textColor = ContextCompat.getColor(requireContext(), R.color.color_363636)
                    val color = ContextCompat.getColor(requireContext(), R.color.color_c4c4c4)
                    constraintLayoutNickname.setBackgroundResource(R.drawable.bg_rectangle_empty_c4c4c4_radius_8)
                    editTextNickname.setTextColor(textColor)
                    textViewNicknameDescription.setTextColor(color)
                    imageViewNicknameDescription.setImageResource(R.drawable.ic_tooltip_c4c4c4)
                    imageViewNicknameDelete.setImageResource(R.drawable.ic_delete_box_c4c4c4)
                }
            }
        }
    }

    private fun showConfirmDialog() {
        commonDialog
            .setTitle(R.string.my_page_setting_question_change_nickname)
            .setDescription(R.string.my_page_setting_introduce_about_changing_nickname)
            .setPositiveButton(R.string.my_page_setting_change_yes) {
                viewModel.updateMyInfo()
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                commonDialog.dismiss()
            }
            .show()
    }
}