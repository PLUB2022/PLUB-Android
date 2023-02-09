package com.plub.presentation.ui.main.home.plubhome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.HomeCategoryPlubType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubHomeRecommendViewType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentHomeBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeCategoryParentAdapter
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeRecommendGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeFragmentViewModel>(
    FragmentHomeBinding::inflate
) {
    companion object{
        const val NOTHING_PLUBBING = 0
    }
    override val viewModel: HomeFragmentViewModel by viewModels()

    private val homeCategoryAdapter: HomeCategoryParentAdapter by lazy {
        HomeCategoryParentAdapter(object : HomeCategoryParentAdapter.HomeCategoryDelegate {
            override fun onCategoryClick(categoryId: Int, categoryName: String) {
                goToCategoryChoice(categoryId, categoryName)
            }
        })
    }

    private val recommendationListAdapter: HomeRecommendGatheringAdapter by lazy {
        HomeRecommendGatheringAdapter(object :
            HomeRecommendGatheringAdapter.HomeRecommendGatheringDelegate {
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
        viewModel.fetchHomePageData()
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
            HomeCategoryPlubType.CATEGORY -> {
                homeCategoryAdapter.submitList(data.categories)
            }
            HomeCategoryPlubType.RECOMMEND_GATHERING -> {
                setDataRecycler(data.plubCardList, data.isLoading)
            }
        }
    }




    private fun setDataRecycler(data: List<RecommendationGatheringResponseVo>, isLoading: Boolean) {
        val mConcatAdapter = ConcatAdapter()
        subListRecommendGatheringList(data, isLoading)
        mConcatAdapter.addAdapter(homeCategoryAdapter)
        mConcatAdapter.addAdapter(recommendationListAdapter)
        binding.recyclerViewMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener((object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisiblePosition = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
                    val isDownScroll = dy > 0
                    viewModel.onScrollChanged(isBottom,isDownScroll)
                }
            }))
            adapter = mConcatAdapter
        }
    }

    private fun subListRecommendGatheringList(list : List<RecommendationGatheringResponseVo>, isLoading : Boolean){
        val loadingList = mutableListOf<RecommendationGatheringResponseVo>()
        loadingList.add(RecommendationGatheringResponseVo(viewType = PlubHomeRecommendViewType.LOADING))
        if(isLoading){
            recommendationListAdapter.submitList(list + loadingList)
        }
        else{
            recommendationListAdapter.submitList(list)
        }
    }

    private fun goToRegisterInterest() {
        val action = HomeFragmentDirections.actionMainToRegisterInterest()
        findNavController().navigate(action)
    }

    private fun goToCategoryChoice(categoryId: Int, categoryName: String) {
        val action =
            HomeFragmentDirections.actionMainToCategoryGathering(categoryId, NOTHING_PLUBBING, categoryName)
        findNavController().navigate(action)
    }

    private fun goToRecruitmentFragment(plubbingId: Int) {
        val action =
            HomeFragmentDirections.actionMainToRecruitment(plubbingId)
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
