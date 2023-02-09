package com.plub.presentation.ui.main.home.categoryChoice

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
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryGatheringBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.adapter.DialogMenuAdapter
import com.plub.presentation.ui.main.home.card.adapter.PlubCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryGatheringFragment :
    BaseFragment<FragmentCategoryGatheringBinding, CategoryGatheringState, CategoryGatheringViewModel>(
        FragmentCategoryGatheringBinding::inflate
    ) {
    private val gatheringListAdapter: PlubCardAdapter by lazy {
        PlubCardAdapter(object : PlubCardAdapter.Delegate {
            override fun onClickBookmark(plubbingId: Int) {
                viewModel.clickBookmark(plubbingId)
            }

            override fun onClickPlubCard(id: Int) {
                goToDetailRecruitment(id)
            }
        })
    }

    private val categoryChoiceFragmentArgs: CategoryGatheringFragmentArgs by navArgs()
    override val viewModel: CategoryGatheringViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            initCategoryRecommendRecyclerView()
            textViewCategoryName.text = categoryChoiceFragmentArgs.categoryName
            includeDataEmpty.buttonGoCreateGathering.setOnClickListener {
                goToCreateGatheringFragment()
            }
        }
        viewModel.fetchRecommendationGatheringData(categoryChoiceFragmentArgs.categoryId)
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    subListGatheringList(it.cardList, it.isLoading)
                    setSortTypeText(it.sortType)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as CategoryGatheringEvent)
                }
            }
        }
    }

    private fun initCategoryRecommendRecyclerView(){
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
                    val lastVisiblePosition = (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
                    val isDownScroll = dy > 0
                    viewModel.onScrollChanged(isBottom,isDownScroll)
                }
            }))
        }
    }

    private fun subListGatheringList(list : List<PlubCardVo>, isLoading : Boolean){
        val loadingList = mutableListOf<PlubCardVo>()
        loadingList.add(PlubCardVo(viewType = PlubCardType.LOADING))
        if(isLoading){
            gatheringListAdapter.submitList(list + loadingList)
        }
        else{
            gatheringListAdapter.submitList(list)
        }
        viewModel.scrollTop()
    }

    private fun goToDetailRecruitment(plubbingId : Int) {
        val action =
            CategoryGatheringFragmentDirections.actionCategoryGatheringToRecruitment(plubbingId)
        findNavController().navigate(action)
    }

    private fun inspectEventFlow(event : CategoryGatheringEvent){
        when(event){
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
            is CategoryGatheringEvent.ScrollTop ->{
                recyclerScrollToTop()
            }
        }
    }

    private fun recyclerScrollToTop(){
        binding.recyclerViewCategoryChoiceList.scrollToPosition(0)
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

    private fun setSortTypeText(sortType: PlubSortType) {
        val sortTypeRes = when (sortType) {
            PlubSortType.POPULAR -> R.string.word_sort_type_popular
            PlubSortType.NEW -> R.string.word_sort_type_new
        }
        binding.textViewSortType.text = getString(sortTypeRes)
    }

    private fun goToSearchFragment() {
        val action = CategoryGatheringFragmentDirections.actionCategoryGatheringToSearching()
        findNavController().navigate(action)
    }

    private fun goToCreateGatheringFragment() {
        val action = CategoryGatheringFragmentDirections.actionCategoryGatheringToCreateGathering()
        findNavController().navigate(action)
    }
}