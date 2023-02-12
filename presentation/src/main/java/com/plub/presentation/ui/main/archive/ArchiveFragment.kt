package com.plub.presentation.ui.main.archive

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter
import com.plub.presentation.ui.main.archive.dialog.ArchiveDetailDialog
import com.plub.presentation.ui.main.home.search.adapter.RecentSearchAdapter
import com.plub.presentation.ui.main.home.searchResult.SearchResultEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveFragment : BaseFragment<FragmentArchiveBinding, ArchivePageState, ArchiveViewModel>(
    FragmentArchiveBinding::inflate
){

    companion object{
        const val ARCHIVE_DETAIL_DIALOG_TAG = "Archive Detail Tag"
    }
    private val archiveAdapter: ArchiveAdapter by lazy {
        ArchiveAdapter(object : ArchiveAdapter.ArchiveDelegate {
            override fun onCardClick(archiveId: Int) {
                viewModel.seeDetailDialog(archiveId)
            }

        })
    }

    private val archiveFragmentArgs : ArchiveFragmentArgs by navArgs()
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
        repeatOnStarted(viewLifecycleOwner){
            launch {
                viewModel.uiState.collect{
                    subListArchive(it.archiveList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ArchiveEvent)
                }
            }
        }
    }

    private fun subListArchive(list : List<ArchiveContentResponseVo>){
        archiveAdapter.submitList(list)
    }

    private fun inspectEventFlow(event : ArchiveEvent){
        when(event){
            is ArchiveEvent.SeeDetailArchiveDialog -> {
                ArchiveDetailDialog(event.responseVo).show(childFragmentManager, ARCHIVE_DETAIL_DIALOG_TAG)
            }
        }
    }
}