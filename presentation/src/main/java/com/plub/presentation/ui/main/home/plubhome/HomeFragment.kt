package com.plub.presentation.ui.main.home.plubhome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentHomeBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeFragmentViewModel>(
    FragmentHomeBinding::inflate
) {
    companion object {
        const val NOTHING_PLUBBING = 0
    }

    override val viewModel: HomeFragmentViewModel by viewModels()

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter(object : HomeAdapter.HomeDelegate {
            override fun onCategoryClick(categoryId: Int, categoryName: String) {
                viewModel.goToCategoryChoice(categoryId, categoryName)
            }

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
            setRecyclerAdapter()
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
                    inspectEvent(it as PlubbingHomeEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: PlubbingHomeEvent) {
        when (event) {
            is PlubbingHomeEvent.GoToSearch -> {
                goToSearchFragment()
            }
            is PlubbingHomeEvent.GoToBookMark -> {
                goToBookmarkFragment()
            }
            is PlubbingHomeEvent.GoToCategoryGathering -> {
                goToCategoryChoice(event.categoryId, event.categoryName)
            }
        }
    }

    private fun submitList(data: HomePageState) {
        if(data.isVisible){
            homeAdapter.submitList(data.homePlubList)
        }
    }

    private fun setRecyclerAdapter() {
        binding.recyclerViewMainPage.apply {
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener((object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisiblePosition =
                        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
                    val isDownScroll = dy > 0
                    viewModel.onScrollChanged(isBottom, isDownScroll)
                }
            }))
            adapter = homeAdapter
        }
    }

//    private fun subListRecommendGatheringList(
//        list: List<RecommendationGatheringResponseVo>,
//        isLoading: Boolean
//    ) {
//        val loadingList = mutableListOf<RecommendationGatheringResponseVo>()
//        loadingList.add(RecommendationGatheringResponseVo(viewType = PlubHomeRecommendViewType.LOADING))
//        if (isLoading) {
//            recommendationListAdapter.submitList(list + loadingList)
//        } else {
//            recommendationListAdapter.submitList(list)
//        }
//    }

    private fun goToRegisterInterest() {
        val action = HomeFragmentDirections.actionMainToRegisterInterest()
        findNavController().navigate(action)
    }

    private fun goToCategoryChoice(categoryId: Int, categoryName: String) {
        val action =
            HomeFragmentDirections.actionMainToCategoryGathering(
                categoryId,
                NOTHING_PLUBBING,
                categoryName
            )
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
