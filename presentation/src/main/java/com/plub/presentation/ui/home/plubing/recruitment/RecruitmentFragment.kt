package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.event.RecruitEvent
import com.plub.presentation.state.DetailRecruitPageState
import com.plub.presentation.ui.common.GridSpaceDecoration
import com.plub.presentation.ui.home.plubing.recruitment.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.home.plubing.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceFragmentArgs
import com.plub.presentation.ui.home.plubing.plubhome.HomeFragmentArgs
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment :
    BaseFragment<FragmentDetailRecruitmentPlubingBinding, DetailRecruitPageState, RecruitmentViewModel>(
        FragmentDetailRecruitmentPlubingBinding::inflate
    ) {
    private val plubbingIdForMain: HomeFragmentArgs by navArgs()
    private val plubbingIdForCategoryChoice: CategoryChoiceFragmentArgs by navArgs()
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
    override val viewModel: RecruitmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            viewModel.updatePlubState(getFragmentArgs())
            viewModel.fetchRecruitmentDetail(getFragmentArgs())
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
                    inspectEventFlow(it as RecruitEvent)
                }
            }
        }
    }

    private fun goToApplyPlubbingFragment(plubbingId: Int) {
        val action = RecruitmentFragmentDirections.actionRecruitmentToApplyPlubbing(
            plubbingId.toString()
        )
        findNavController().navigate(action)
    }

    private fun getFragmentArgs(): Int {
        var id = ""
        if (plubbingIdForMain.plubbingId.equals("0")) {
            id = plubbingIdForCategoryChoice.plubbingId
        } else
            id = plubbingIdForMain.plubbingId
        return id.toInt()
    }

    private fun inspectEventFlow(event : RecruitEvent){
        when(event){
            RecruitEvent.GoToApplyPlubbingFragment-> goToApplyPlubbingFragment(getFragmentArgs())
            RecruitEvent.GoToProfileFragment ->{}
            RecruitEvent.GoToBack -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun goToProfile(accountId: Int) {

    }

    private fun initDetailPage(data: DetailRecruitPageState) {
        binding.apply {
            constraintLayoutTop.bringToFront()
            //GlideUtil.loadImage(root.context, data.plubbingMainImage, imageViewPlubbingImage)

            detailRecruitCategoryAdapter.submitList(data.categories)
            recyclerViewPlubbingHobby.apply {
                layoutManager = GridLayoutManager(context, 4)
                addItemDecoration(GridSpaceDecoration(4, 8.px, 8.px, false))
                adapter = detailRecruitCategoryAdapter
            }

            detailRecruitProfileAdapter.submitList(data.joinedAccounts)
            recyclerViewPlubbingPeopleProfile.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = detailRecruitProfileAdapter
            }
        }
    }
}