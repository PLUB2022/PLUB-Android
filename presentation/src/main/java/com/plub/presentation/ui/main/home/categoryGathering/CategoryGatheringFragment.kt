package com.plub.presentation.ui.main.home.categoryGathering

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentCategoryGatheringBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.adapter.DialogMenuAdapter
import com.plub.presentation.ui.main.home.card.adapter.PlubCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryGatheringFragment :
    BaseTestFragment<FragmentCategoryGatheringBinding, CategoryGatheringState, CategoryGatheringViewModel>(
        FragmentCategoryGatheringBinding::inflate
    ) {
    private val gatheringListAdapter: PlubCardAdapter by lazy {
        PlubCardAdapter(object : PlubCardAdapter.Delegate {
            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
            }

            override fun onClickPlubCard(id: Int) {
                viewModel.goToDetailRecruitment(id)
            }
        })
    }

    private val categoryChoiceFragmentArgs: CategoryGatheringFragmentArgs by navArgs()
    override val viewModel: CategoryGatheringViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            initCategoryRecommendRecyclerView()
        }

        viewModel.updateCategoryNameAndId(categoryChoiceFragmentArgs.categoryName, categoryChoiceFragmentArgs.categoryId)

        viewModel.fetchRecommendationGatheringData(
            categoryChoiceFragmentArgs.filter
        )
    }

    private fun initCategoryRecommendRecyclerView() {
        binding.recyclerViewCategoryChoiceList.apply {
            layoutManager = GridLayoutManager(context, PlubCardType.TOTAL_SPAN_SIZE).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val viewType = gatheringListAdapter.getItemViewType(position)
                        val cardType = PlubCardType.valueOf(viewType)
                        return cardType.spanSize
                    }
                }
            }
            adapter = gatheringListAdapter

            addOnScrollListener((object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisiblePosition =
                        (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
                    val isDownScroll = dy > 0
                    viewModel.onScrollChanged(isBottom, isDownScroll)
                }
            }))
        }
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.cardList.collect {
                    subListGatheringList(it)
                }
            }
            launch {
                viewModel.uiState.sortType.collect{
                    setSortTypeText(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as CategoryGatheringEvent)
                }
            }
        }
    }


    private fun subListGatheringList(list: List<PlubCardVo>) {
        gatheringListAdapter.submitList(list)
        viewModel.scrollTop()
    }

    private fun setSortTypeText(sortType: PlubSortType) {
        viewModel.updateSortTypeName(sortType)
    }

    private fun inspectEventFlow(event: CategoryGatheringEvent) {
        when (event) {
            is CategoryGatheringEvent.GoToBack -> {
                findNavController().popBackStack()
            }
            is CategoryGatheringEvent.ShowSelectSortTypeBottomSheetDialog -> {
                showSelectSortTypeDialog(event.selectedItem)
            }
            is CategoryGatheringEvent.GoToSearch -> {
                goToSearchFragment()
            }
            is CategoryGatheringEvent.GoToCreate -> {
                goToCreateGatheringFragment()
            }
            is CategoryGatheringEvent.GoToRecruit -> {
                goToDetailRecruitment(event.id)
            }
            is CategoryGatheringEvent.ScrollTop -> {
                recyclerScrollToTop()
            }
            is CategoryGatheringEvent.GoToFilter -> {
                goToCategoryGatheringFilter()
            }
        }
    }

    private fun showSelectSortTypeDialog(selectedMenuType: DialogMenuItemType) {
        val title = getString(R.string.word_sort)
        SelectMenuBottomSheetDialog.newInstance(
            DialogMenuType.SORT_TYPE,
            title,
            DialogMenuAdapter.VIEW_TYPE_SPINNER,
            selectedMenuType
        ) {
            viewModel.onClickSortMenuItemType(it)
        }.show(parentFragmentManager, "")
    }

    private fun goToSearchFragment() {
        val action = CategoryGatheringFragmentDirections.actionCategoryGatheringToSearching()
        findNavController().navigate(action)
    }

    private fun goToCreateGatheringFragment() {
        val action = CategoryGatheringFragmentDirections.actionCategoryGatheringToCreateGathering()
        findNavController().navigate(action)
    }

    private fun goToDetailRecruitment(plubbingId: Int) {
        val action =
            CategoryGatheringFragmentDirections.actionCategoryGatheringToRecruitment(plubbingId)
        findNavController().navigate(action)
    }

    private fun recyclerScrollToTop() {
        binding.recyclerViewCategoryChoiceList.scrollToPosition(0)
    }

    private fun goToCategoryGatheringFilter() {
        val action = CategoryGatheringFragmentDirections.actionCategoryGatheringToFilter(
            categoryChoiceFragmentArgs.categoryId,
            categoryChoiceFragmentArgs.categoryName
        )
        findNavController().navigate(action)
    }
}