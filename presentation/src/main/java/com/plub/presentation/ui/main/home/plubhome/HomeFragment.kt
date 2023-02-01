package com.plub.presentation.ui.main.home.plubhome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.MainPageCategoryPlubType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentHomeBinding
import com.plub.presentation.event.PlubbingMainEvent
import com.plub.presentation.ui.main.home.plubhome.adapter.MainCategoryAdapter
import com.plub.presentation.ui.main.home.plubhome.adapter.MainRecommendGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeFragmentViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeFragmentViewModel by viewModels()

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

    private fun submitList(data: HomePageState) {
        when (data.categoryOrPlub) {
            MainPageCategoryPlubType.CATEGORY -> {
                mainCategoryAdapter.submitList(arrayListOf(data.categoryVo))
            }
            MainPageCategoryPlubType.PLUB -> {
                setDataRecycler(data.plubCardList)
            }
        }
    }


    private fun setDataRecycler(data: List<RecommendationGatheringResponseVo>) {
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
        val action = HomeFragmentDirections.actionMainToRegisterInterest()
        findNavController().navigate(action)
    }

    private fun goToCategoryChoice(categoryId: Int, categoryName: String) {
        val action =
            HomeFragmentDirections.actionMainToCategoryChoice(categoryId.toString(), categoryName)
        findNavController().navigate(action)
    }

    private fun goToRecruitmentFragment(plubbingId: Int) {
        val action =
            HomeFragmentDirections.actionMainToRecruitment(plubbingId.toString())
        findNavController().navigate(action)
    }

    private fun goToSearchFragment() {
        val action = HomeFragmentDirections.actionMainToSearching()
        findNavController().navigate(action)
    }

    private fun goToBookmarkFragment() {
        val action = HomeFragmentDirections.actionMainToBookmark()
        findNavController().navigate(action)
    }

}