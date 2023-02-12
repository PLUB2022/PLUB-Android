package com.plub.presentation.ui.main.archive

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
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
                //viewModel.seeDetailDialog(archiveId)
                val list = arrayListOf<String>("https://plub.s3.ap-northeast-2.amazonaws.com/plubbing/mainImage/sports1.png" , "https://plub.s3.ap-northeast-2.amazonaws.com/plubbing/mainImage/sports1.png", "https://plub.s3.ap-northeast-2.amazonaws.com/plubbing/mainImage/2625879414%40KAKAO_873db2c5-613a-41ea-84bd-dec8d194d629_.jpeg")
                val response = ArchiveDetailResponseVo(list, archiveId,"2023-02-12", "테스트 아카이브")
                ArchiveDetailDialog(response).show(childFragmentManager, ARCHIVE_DETAIL_DIALOG_TAG)
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