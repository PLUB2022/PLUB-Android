package com.plub.presentation.ui.main.recruitment.hostrecruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentHostDetailRecruitmentPlubbingBinding
import com.plub.presentation.event.HostDetailPageEvent
import com.plub.presentation.state.DetailRecruitPageState
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.home.plubing.recruitment.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.home.plubing.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HostRecruitmentFragment :
    BaseFragment<FragmentHostDetailRecruitmentPlubbingBinding, DetailRecruitPageState, HostRecruitmentViewModel>(
        FragmentHostDetailRecruitmentPlubbingBinding::inflate
    ) {
    private val detailRecruitProfileAdapter: DetailRecruitProfileAdapter by lazy {
        DetailRecruitProfileAdapter(object : DetailRecruitProfileAdapter.DetailProfileDegelate {
            override fun onProfileClick(accountId: Int) {
                goToProfile(accountId)
            }

        })
    }
    private val detailRecruitCategoryAdapter : DetailRecruitCategoryAdapter by lazy {
        DetailRecruitCategoryAdapter()
    }
    override val viewModel: HostRecruitmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            //viewModel.fetchRecruitmentDetail(plubbingId = )
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    initDetailPage(it)
                }
            }
            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as HostDetailPageEvent)
                }
            }
        }
    }

    private fun goToProfile(accountId: Int) {

    }

    private fun initDetailPage(data: DetailRecruitPageState) {
        binding.apply {
            constraintLayoutTop.bringToFront()
            detailRecruitCategoryAdapter.submitList(data.categories)
            recyclerViewPlubbingHobby.apply {
                layoutManager = GridLayoutManager(context, 4)
                addItemDecoration(GridSpaceDecoration(4, 8.px ,8.px,false))
                adapter = detailRecruitCategoryAdapter
            }

            detailRecruitProfileAdapter.submitList(data.joinedAccounts)
            recyclerViewPlubbingPeopleProfile.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = detailRecruitProfileAdapter
            }
        }
    }

    private fun inspectEvent(event : HostDetailPageEvent){
        when(event){
            is HostDetailPageEvent.GoToBack->{
                findNavController().popBackStack()
            }
            is HostDetailPageEvent.GoToEditFragment->{

            }
            is HostDetailPageEvent.GoToSeeApplicants->{

            }
        }
    }
}