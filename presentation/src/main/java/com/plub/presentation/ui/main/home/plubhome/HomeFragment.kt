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
        private const val NOTHING_PLUBBING = 0
    }

    override val viewModel: HomeFragmentViewModel by viewModels()

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter(object : HomeAdapter.HomeDelegate {
            override fun onCategoryClick(categoryId: Int, categoryName: String) {
                viewModel.goToCategoryChoice(categoryId, categoryName)
            }

            override fun onClickGoRecruitDetail(plubbingId: Int) {
                viewModel.goToRecruitment(plubbingId)
            }

            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
            }

            override fun onClickRegister() {
                viewModel.goToRegisterInterest()
            }

            override fun onClickSetting() {
                //Setting 이동
            }
        })

    }

    override fun initView() {

        binding.apply {
            vm = viewModel
            recyclerViewMainPage.apply {
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

    private fun submitList(data: HomePageState) {
        if(data.isVisible){
            homeAdapter.submitList(data.homePlubList)
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
                goToCategoryGathering(event.categoryId, event.categoryName)
            }
            is PlubbingHomeEvent.GoToRecruitment -> {
                goToRecruitmentFragment(event.plubbingId)
            }
            is PlubbingHomeEvent.GoToRegisterInterest -> {
                goToRegisterInterest()
            }
        }
    }

    private fun goToSearchFragment() {
        val action = HomeFragmentDirections.actionMainToSearching()
        findNavController().navigate(action)
    }

    private fun goToBookmarkFragment() {
        val action = HomeFragmentDirections.actionMainToBookmark()
        findNavController().navigate(action)
    }

    private fun goToCategoryGathering(categoryId: Int, categoryName: String) {
        val action =
            HomeFragmentDirections.actionMainToCategoryGathering(
                categoryId,
                NOTHING_PLUBBING,
                categoryName
            )
        findNavController().navigate(action)
    }


    private fun goToRegisterInterest() {
        val action = HomeFragmentDirections.actionMainToRegisterInterest()
        findNavController().navigate(action)
    }


    private fun goToRecruitmentFragment(plubbingId: Int) {
        val action =
            HomeFragmentDirections.actionMainToRecruitment(plubbingId)
        findNavController().navigate(action)
    }
}
