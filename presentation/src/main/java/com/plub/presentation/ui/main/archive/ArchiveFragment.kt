package com.plub.presentation.ui.main.archive

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveBinding
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter
import com.plub.presentation.ui.main.archive.bottomsheet.dots.ArchiveDotsMenuBottomSheetFragment
import com.plub.presentation.ui.main.archive.bottomsheet.upload.ArchiveBottomSheetFragment
import com.plub.presentation.ui.main.archive.dialog.ArchiveDetailDialogFragment
import com.plub.presentation.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ArchiveFragment : BaseFragment<FragmentArchiveBinding, ArchivePageState, ArchiveViewModel>(
    FragmentArchiveBinding::inflate
) {

    companion object {
        const val UPLOAD_TYPE = 0
        const val EDIT_TYPE = 1
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
        viewModel.fetchArchivePage()
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    subListArchive(it.archiveList)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as ArchiveEvent)
                }
            }
        }
    }

    private fun subListArchive(list: List<ArchiveContentResponseVo>) {
        archiveAdapter.submitList(list)
    }

    private fun inspectEventFlow(event: ArchiveEvent) {
        when (event) {
            is ArchiveEvent.SeeDetailArchiveDialog -> {
                ArchiveDetailDialogFragment(event.responseVo).show(
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
        }
    }

    private fun clickBottomSheet() {
        PermissionManager.createGetImagePermission {
            showBottomSheetDialogSelectImage()
        }
    }

    private fun showBottomSheetDialogSelectImage() {
        ArchiveBottomSheetFragment(object : ArchiveBottomSheetFragment.ArchiveBottomSheetDelegate {
            override fun onSuccessGetImage(file: File?) {
                viewModel.uploadImageFile(file)
            }
        }).show(childFragmentManager, "")
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
            archiveId = 0,
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
            image = "",
            title = title
        )
        findNavController().navigate(action)
    }

    private fun goToReport(archiveId: Int){

    }
}