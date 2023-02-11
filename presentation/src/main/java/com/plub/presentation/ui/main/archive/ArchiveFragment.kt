package com.plub.presentation.ui.main.archive

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentArchiveBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.archive.adapter.ArchiveAdapter
import com.plub.presentation.ui.main.home.search.adapter.RecentSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchiveFragment : BaseFragment<FragmentArchiveBinding, ArchivePageState, ArchiveViewModel>(
    FragmentArchiveBinding::inflate
){

    private val archiveAdapter: ArchiveAdapter by lazy {
        ArchiveAdapter(object : ArchiveAdapter.ArchiveDelegate {

        })
    }

    private val archiveFragmentArgs : ArchiveFragmentArgs by navArgs()
    override val viewModel: ArchiveViewModel by viewModels()
    override fun initView() {
        binding.apply {
            vm = viewModel
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
        }
    }

    private fun subListArchive(list : List<ArchiveContentResponseVo>){
        archiveAdapter.submitList(list)
    }
}