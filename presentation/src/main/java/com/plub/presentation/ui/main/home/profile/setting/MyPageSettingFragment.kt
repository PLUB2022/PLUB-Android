package com.plub.presentation.ui.main.home.profile.setting

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
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageSettingBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.home.profile.setting.dialog.MyPageSettingConfirmDialogFragment
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.IntentUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageSettingFragment :
    BaseFragment<FragmentMyPageSettingBinding, MyPageSettingState, MyPageSettingViewModel>(
        FragmentMyPageSettingBinding::inflate
    ) {

    private lateinit var albumLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    override val viewModel: MyPageSettingViewModel by viewModels()

    private val myPageSettingConfirmDialogFragment : MyPageSettingConfirmDialogFragment by lazy {
        MyPageSettingConfirmDialogFragment(object : MyPageSettingConfirmDialogFragment.Delegate{
            override fun onYesButtonClick() {
                viewModel.
            }

        })
    }

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
        }.show(parentFragmentManager,"")
    }

    private fun setAlbumLauncher() {
        albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            result.data?.data?.let {
                viewModel.onSelectImageFromAlbum(it)
            }
        }
    }

    private fun setCameraLauncher() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
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
                viewModel.uiState.collect {
                    setProfileImage(it.profileImage)
                    nicknameIsActiveState(it.nicknameIsActive)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as MyPageSettingEvent)
                }
            }
        }
    }

    private fun setProfileImage(image : String){
        binding.apply {
            if(image == "" || image == null) imageViewProfile.setImageResource(R.drawable.iv_default_profile) else GlideUtil.loadImage(root.context, image, imageViewProfile)
            imageViewProfile.clipToOutline = true
        }
    }

    private fun inspectEventFlow(event: MyPageSettingEvent) {
        when(event) {
            is MyPageSettingEvent.GoToAlbum -> {
                val intent = IntentUtil.getSingleImageIntent()
                albumLauncher.launch(intent)
            }
            is MyPageSettingEvent.GoToCamera -> {
                val intent = IntentUtil.getOpenCameraIntent(event.uri)
                cameraLauncher.launch(intent)
            }
            is MyPageSettingEvent.ShowSelectImageBottomSheetDialog -> showBottomSheetDialogSelectImage()
            is MyPageSettingEvent.CropImageAndOptimize -> startCropImage(event.cropImageContractOptions)
            is MyPageSettingEvent.ShowDialog -> { showConfirmDialog() }
            is MyPageSettingEvent.GoToBack -> findNavController().popBackStack()
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

    private fun showConfirmDialog(){
        myPageSettingConfirmDialogFragment.show(childFragmentManager, "")
    }
}