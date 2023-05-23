package com.plub.presentation.ui.main.archive

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.ToastType
import com.plub.domain.model.sealed.ReportType
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentArchiveBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter
import com.plub.presentation.ui.main.archive.dialog.ArchiveDetailDialogFragment
import com.plub.presentation.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveFragment : BaseTestFragment<FragmentArchiveBinding, ArchivePageState, ArchiveViewModel>(
    FragmentArchiveBinding::inflate
) {

    companion object {
        const val UPLOAD_TYPE = 0
        const val EDIT_TYPE = 1

        const val EMPTY_ARCHIVE_ID = 0
        const val EMPTY_IMAGE = ""
    }

    private val archiveAdapter: ArchiveAdapter by lazy {
        ArchiveAdapter(object : ArchiveAdapter.ArchiveDelegate {
            override fun onCardClick(archiveId: Int) {
                viewModel.seeDetailDialog(archiveId)
            }

            override fun onDotsClick(type: ArchiveAccessType, archiveId : Int) {
                viewModel.seeBottomSheet(type, archiveId)
            }
        })
    }

    private val gatheringImageFromCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.proceedGatheringImageFromCameraResult(it)
        }

    private val gatheringImageFromGalleryResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.proceedGatheringImageFromGalleryResult(result)
    }

    override val viewModel: ArchiveViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerViewArchive.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = archiveAdapter
                infiniteScrolls { viewModel.onScrollChanged() }
            }
        }
        viewModel.refresh()
        viewModel.onFetchArchiveList()
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.archiveList.collect {
                    archiveAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as ArchiveEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: ArchiveEvent) {
        when (event) {
            is ArchiveEvent.SeeDetailArchiveDialog -> {
                ArchiveDetailDialogFragment.newInstance(event.responseVo).show(
                    childFragmentManager,
                    ""
                )
            }
            is ArchiveEvent.GoToBack -> {
                findNavController().popBackStack()
            }
            is ArchiveEvent.ClickUploadBottomSheet -> {
                clickBottomSheet()
            }
            is ArchiveEvent.GoToArchiveUpload -> {
                goToArchiveUpload(event.fileUri)
            }
            is ArchiveEvent.GoToReport -> {
                goToReport(event.archiveId)
            }
            is ArchiveEvent.GoToEdit -> {
                goToArchiveEdit(event.archiveId)
            }
            is ArchiveEvent.CropImageAndOptimize -> { startCropImage(event.cropImageContractOptions) }
            is ArchiveEvent.GoToAlbum -> { getImageFromGallery() }
            is ArchiveEvent.GoToCamera -> { getImageFromCamera(event.uri) }
            is ArchiveEvent.SeeDotsAuthorBottomSheet -> {showBottomSheetDialogSelectDots(DialogMenuType.ARCHIVE_AUTHOR_TYPE, event.archiveId)}
            is ArchiveEvent.SeeDotsHostBottomSheet -> {showBottomSheetDialogSelectDots(DialogMenuType.ARCHIVE_HOST_TYPE, event.archiveId)}
            is ArchiveEvent.SeeDotsNormalBottomSheet -> {showBottomSheetDialogSelectDots(DialogMenuType.ARCHIVE_NORMAL_TYPE, event.archiveId)}
            is ArchiveEvent.FailUpload -> {
                PlubToast.createToast(ToastType.ERROR, binding.root.context, R.string.error_image_upload)
            }
        }
    }

    private fun clickBottomSheet() {
        PermissionManager.createGetImagePermission {
            showBottomSheetDialogSelectImage()
        }
    }

    private fun showBottomSheetDialogSelectDots(type : DialogMenuType, archiveId : Int) {
        SelectMenuBottomSheetDialog.newInstance(type) {
            viewModel.onClickDotsMenuItemType(it, archiveId)
        }.show(parentFragmentManager, "")
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun goToArchiveUpload(imageUri: String) {
        val action = ArchiveFragmentDirections.actionArchiveToUpdate(
            type = UPLOAD_TYPE,
            archiveId = EMPTY_ARCHIVE_ID,
            image = imageUri
        )
        findNavController().navigate(action)
    }

    private fun goToArchiveEdit(archiveId: Int) {
        val action = ArchiveFragmentDirections.actionArchiveToUpdate(
            type = EDIT_TYPE,
            archiveId = archiveId,
            image = EMPTY_IMAGE
        )
        findNavController().navigate(action)
    }

    private fun goToReport(archiveId: Int){
        val action = ArchiveFragmentDirections.actionArchiveToReport(
            type = ReportType.ArchiveReport(PlubingInfo.info.plubingId, archiveId)
        )
        findNavController().navigate(action)
    }

    private fun getImageFromCamera(uri: Uri) {
        val intent = IntentUtil.getOpenCameraIntent(uri)
        gatheringImageFromCameraResult.launch(intent)
    }

    private fun getImageFromGallery() {
        val intent = IntentUtil.getSingleImageIntent()
        gatheringImageFromGalleryResult.launch(intent)
    }

    private fun startCropImage(option: CropImageContractOptions) {
        cropImage.launch(option)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        viewModel.proceedCropImageResult(result)
    }
}