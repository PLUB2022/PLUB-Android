package com.plub.presentation.ui.main.home.profile.recruiting

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageRecruitingGatheringBinding
import com.plub.presentation.ui.main.home.profile.adapter.MyPageDetailPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitingGatheringFragment :
    BaseFragment<FragmentMyPageRecruitingGatheringBinding, RecruitingGatheringState, RecruitingGatheringViewModel>(
        FragmentMyPageRecruitingGatheringBinding::inflate
    ) {

    private val myPageDetailPageAdapter : MyPageDetailPageAdapter by lazy {
        MyPageDetailPageAdapter()
    }

    override val viewModel: RecruitingGatheringViewModel by viewModels()

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