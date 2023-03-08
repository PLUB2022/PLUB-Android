package com.plub.presentation.ui.main.home.profile.waiting

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageWaitingGatheringBinding
import com.plub.presentation.ui.main.home.profile.adapter.MyPageDetailPageAdapter
import com.plub.presentation.ui.main.home.profile.recruiting.MyPageApplicantsGatheringState
import com.plub.presentation.ui.main.home.profile.recruiting.RecruitingGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WaitingGatheringFragment :
    BaseFragment<FragmentMyPageWaitingGatheringBinding, MyPageApplicantsGatheringState, WaitingGatheringViewModel>(
        FragmentMyPageWaitingGatheringBinding::inflate
    ) {

    private val myPageDetailPageAdapter : MyPageDetailPageAdapter by lazy {
        MyPageDetailPageAdapter()
    }

    override val viewModel: WaitingGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerViewMyPageContent.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = myPageDetailPageAdapter
            }
        }

        viewModel.getPageDetail()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    myPageDetailPageAdapter.submitList(it.detailList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                }
            }
        }
    }
}