package com.plub.presentation.ui.home.plubing.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.MainPageCategoryPlubType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMainBinding
import com.plub.presentation.event.PlubbingMainEvent
import com.plub.presentation.state.MainPageState
import com.plub.presentation.ui.home.plubing.main.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainPageState, MainFragmentViewModel>(
    FragmentMainBinding::inflate
) {
    override val viewModel: MainFragmentViewModel by viewModels()

    private val mainCategoryAdapter: MainCategoryAdapter by lazy {
        MainCategoryAdapter(object : MainCategoryAdapter.MainCategoryDelegate {
            override fun onClick(categoryId: Int, categoryName: String) {
                goToCategoryChoice(categoryId, categoryName)
            }
        })
    }

    private val recommendationListAdapter: MainRecommendGatheringAdapter by lazy {
        MainRecommendGatheringAdapter(object :
            MainRecommendGatheringAdapter.MainRecommendGatheringDelegate {
            override fun onClickGoRecruitDetail(plubbingId: Int) {
                goToRecruitmentFragment(plubbingId)
            }

            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
            }

            override fun onClickRegister() {
                goToRegisterInterest()
            }

            override fun onClickSetting() {
                //Setting 이동
            }
        })
    }

    override fun initView() {

        binding.apply {
            vm = viewModel
        }
        viewModel.fetchMainPageData()
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as PlubbingMainEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: PlubbingMainEvent) {
        when (event) {
            is PlubbingMainEvent.GoToSearch -> {
                goToSearchFragment()
            }
            is PlubbingMainEvent.GoToBookMark -> {
                goToBookmarkFragment()
            }
        }
    }

    private fun submitList(data: MainPageState) {
        when (data.categoryOrPlub) {
            MainPageCategoryPlubType.CATEGORY -> {
                mainCategoryAdapter.submitList(arrayListOf(data.categoryVo))
            }
            MainPageCategoryPlubType.PLUB -> {
                HasDataRecycler(data.plubCardList)
            }
        }
    }


    private fun HasDataRecycler(data: List<RecommendationGatheringResponseVo>) {
        val mConcatAdapter = ConcatAdapter()
        recommendationListAdapter.submitList(data)
        mConcatAdapter.addAdapter(mainCategoryAdapter)
        mConcatAdapter.addAdapter(recommendationListAdapter)
        binding.recyclerViewMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mConcatAdapter
        }
    }

    private fun goToRegisterInterest() {
        val action = MainFragmentDirections.actionMainToRegisterInterest()
        findNavController().navigate(action)
    }

    private fun goToCategoryChoice(categoryId: Int, categoryName: String) {
        val action =
            MainFragmentDirections.actionMainToCategoryChoice(categoryId.toString(), categoryName)
        findNavController().navigate(action)
    }

    private fun goToRecruitmentFragment(plubbingId: Int) {
        val action =
            MainFragmentDirections.actionMainToRecruitment(plubbingId.toString())
        findNavController().navigate(action)
    }

    private fun goToSearchFragment() {
        val action = MainFragmentDirections.actionMainToSearching()
        findNavController().navigate(action)
    }

    private fun goToBookmarkFragment() {
        val action = MainFragmentDirections.actionMainToBookmark()
        findNavController().navigate(action)
    }

}