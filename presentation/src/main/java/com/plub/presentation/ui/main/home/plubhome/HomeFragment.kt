package com.plub.presentation.ui.main.home.plubhome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentHomeBinding
import com.plub.presentation.parcelableVo.ParseCategoryFilterVo
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeAdapter
import com.plub.presentation.util.infiniteScrolls
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseTestFragment<FragmentHomeBinding, HomePageState, HomeFragmentViewModel>(
    FragmentHomeBinding::inflate
) {

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
                viewModel.goToRegisterInterest()
            }
        })

    }

    override fun initView() {

        binding.apply {
            vm = viewModel
            recyclerViewMainPage.apply {
                layoutManager = LinearLayoutManager(context)
                infiniteScrolls { viewModel.onScrollChanged() }
                adapter = homeAdapter
            }
        }
        viewModel.fetchHomePageData()
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.homePlubList.collect {
                    homeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as HomeEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GoToSearch -> {
                goToSearchFragment()
            }
            is HomeEvent.GoToBookMark -> {
                goToBookmarkFragment()
            }
            is HomeEvent.GoToCategoryGathering -> {
                goToCategoryGathering(event.categoryId, event.categoryName)
            }
            is HomeEvent.GoToRecruitment -> {
                goToRecruitmentFragment(event.plubbingId)
            }
            is HomeEvent.GoToRegisterInterest -> {
                goToRegisterInterest()
            }
            is HomeEvent.GoToCreateGathering -> {
                goToCreateGathering()
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
        val filter = ParseCategoryFilterVo()
        val action =
            HomeFragmentDirections.actionMainToCategoryGathering(
                categoryId = categoryId,
                categoryName = categoryName,
                filter = filter
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

    private fun goToCreateGathering(){
        val action = HomeFragmentDirections.actionMainToCreateGathering()
        findNavController().navigate(action)
    }
}
