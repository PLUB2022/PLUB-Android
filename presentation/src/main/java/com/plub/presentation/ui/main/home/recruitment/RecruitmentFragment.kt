package com.plub.presentation.ui.main.home.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.ui.main.home.categoryChoice.CategoryChoiceFragmentArgs
import com.plub.presentation.ui.main.home.plubhome.HomeFragmentArgs
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment :
    BaseFragment<FragmentDetailRecruitmentPlubingBinding, DetailRecruitPageState, RecruitmentViewModel>(
        FragmentDetailRecruitmentPlubingBinding::inflate
    ) {


    private val recruitmentFragmentArgs : RecruitmentFragmentArgs by navArgs()
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
            viewModel.updatePlubState(recruitmentFragmentArgs.plubbingId)
            viewModel.fetchRecruitmentDetail(recruitmentFragmentArgs.plubbingId)
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
            plubbingId
        )
        findNavController().navigate(action)
    }


    private fun inspectEventFlow(event : RecruitEvent){
        when(event){
            RecruitEvent.GoToApplyPlubbingFragment-> goToApplyPlubbingFragment(recruitmentFragmentArgs.plubbingId)
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
            GlideUtil.loadImage(root.context, data.plubbingMainImage, imageViewPlubbingImage)

            detailRecruitCategoryAdapter.submitList(data.categories)
            recyclerViewPlubbingHobby.apply {
                layoutManager = FlexboxLayoutManager(context)
                adapter = detailRecruitCategoryAdapter
            }

            detailRecruitProfileAdapter.submitList(data.joinedAccounts)
            recyclerViewPlubbingPeopleProfile.apply {
                addItemDecoration(GridSpaceDecoration(8, 4.px, 0,false))
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = detailRecruitProfileAdapter
            }
        }
    }
}