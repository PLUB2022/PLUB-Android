package com.plub.presentation.ui.main.archive

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveBinding
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter
import com.plub.presentation.ui.main.archive.bottomsheet.author.ArchiveAuthorBottomSheetFragment
import com.plub.presentation.ui.main.archive.bottomsheet.host.ArchiveHostBottomSheetFragment
import com.plub.presentation.ui.main.archive.bottomsheet.normal.ArchiveNormalBottomSheetFragment
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

            override fun onDotsClick(type: String, archiveId : Int) {
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
            }
        }
        viewModel.fetchArchivePage(archiveFragmentArgs.title, archiveFragmentArgs.plubbingId)
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
            is ArchiveEvent.SeeAuthorBottomSheet -> {
                showBottomSheetAuthor(event.archiveId)
            }
            is ArchiveEvent.SeeHostBottomSheet -> {
                showBottomSheetHost(event.archiveId)
            }
            is ArchiveEvent.SeeNormalBottomSheet -> {
                showBottomSheetNormal(event.archiveId)
            }
            is ArchiveEvent.GoToReport -> {
                goToReport(event.archiveId)
            }
            is ArchiveEvent.GoToEdit -> {
                goToArchiveEdit(event.title, event.archiveId)
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

    private fun showBottomSheetAuthor(archiveId : Int){
        ArchiveAuthorBottomSheetFragment(archiveFragmentArgs.plubbingId, archiveId, object : ArchiveAuthorBottomSheetFragment.ArchiveAuthorDelegate{
            override fun onDelete() {
                viewModel.deleteArchive(archiveId)
            }

            override fun onClickEdit() {
                viewModel.goToEdit(archiveId)
            }

        }).show(childFragmentManager, "")
    }

    private fun showBottomSheetHost(archiveId : Int){
        ArchiveHostBottomSheetFragment(archiveFragmentArgs.plubbingId, archiveId, object : ArchiveHostBottomSheetFragment.ArchiveHostDelegate{
            override fun onDelete() {
                viewModel.deleteArchive(archiveId)
            }

            override fun goToReport() {
                viewModel.goToReport(archiveId)
            }

        }).show(childFragmentManager, "")
    }

    private fun showBottomSheetNormal(archiveId : Int){
        ArchiveNormalBottomSheetFragment(object : ArchiveNormalBottomSheetFragment.ArchiveNormalDelegate{
            override fun goToReport() {
                viewModel.goToReport(archiveId)
            }
        }).show(childFragmentManager, "")
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