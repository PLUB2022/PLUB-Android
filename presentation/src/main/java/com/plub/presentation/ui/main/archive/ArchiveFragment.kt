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
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentArchiveBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter
import com.plub.presentation.ui.main.archive.bottomsheet.dots.ArchiveDotsMenuBottomSheetFragment
import com.plub.presentation.ui.main.archive.dialog.ArchiveDetailDialogFragment
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
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

    private val archiveFragmentArgs: ArchiveFragmentArgs by navArgs()
    override val viewModel: ArchiveViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerViewArchive.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = archiveAdapter

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisiblePosition =
                            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
                        val isDownScroll = dy > 0
                        viewModel.onScrollChanged(isBottom, isDownScroll)
                    }
                })
            }
        }
        viewModel.setTitleAndPlubbingId(archiveFragmentArgs.title, archiveFragmentArgs.plubbingId)
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
                goToArchiveUpload(event.fileUri, event.title)
            }
            is ArchiveEvent.GoToReport -> {
                goToReport(event.archiveId)
            }
            is ArchiveEvent.GoToEdit -> {
                goToArchiveEdit(event.title, event.archiveId)
            }
            is ArchiveEvent.SeeDotsBottomSheet -> {
                showBottomSheetDots(event.archiveId, event.archiveAccessType)
            }
            is ArchiveEvent.CropImageAndOptimize -> { startCropImage(event.cropImageContractOptions) }
            is ArchiveEvent.GoToAlbum -> { getImageFromGallery() }
            is ArchiveEvent.GoToCamera -> { getImageFromCamera(event.uri) }
        }
    }

    private fun clickBottomSheet() {
        PermissionManager.createGetImagePermission {
            showBottomSheetDialogSelectImage()
        }
    }

    private fun showBottomSheetDialogSelectImage() {
        SelectMenuBottomSheetDialog.newInstance(DialogMenuType.IMAGE) {
            viewModel.onClickImageMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun showBottomSheetDots(archiveId : Int, accessType: ArchiveAccessType){
        ArchiveDotsMenuBottomSheetFragment(
            archiveFragmentArgs.plubbingId,
            archiveId,
            accessType,
            object : ArchiveDotsMenuBottomSheetFragment.ArchiveDotsDelegate{
                override fun onDelete() {
                    viewModel.deleteArchive(archiveId)
                }

                override fun onClickEdit() {
                    viewModel.goToEdit(archiveId)
                }

                override fun onClickReport() {
                    viewModel.goToReport(archiveId)
                }
            }).show(childFragmentManager,"")
    }

    private fun goToArchiveUpload(imageUri: String, title : String) {
        val action = ArchiveFragmentDirections.actionArchiveToUpdate(
            type = UPLOAD_TYPE,
            plubbingId = archiveFragmentArgs.plubbingId,
            archiveId = EMPTY_ARCHIVE_ID,
            image = imageUri,
            title = title
        )
        findNavController().navigate(action)
    }

    private fun goToArchiveEdit(title : String, archiveId: Int) {
        val action = ArchiveFragmentDirections.actionArchiveToUpdate(
            type = EDIT_TYPE,
            plubbingId = archiveFragmentArgs.plubbingId,
            archiveId = archiveId,
            image = EMPTY_IMAGE,
            title = title
        )
        findNavController().navigate(action)
    }

    private fun goToReport(archiveId: Int){

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