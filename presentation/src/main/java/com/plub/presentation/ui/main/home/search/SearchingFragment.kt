package com.plub.presentation.ui.main.home.search

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.enums.PlubSortType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSearchingBinding
import com.plub.presentation.databinding.IncludeTabSearchResultBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.adapter.DialogMenuAdapter
import com.plub.presentation.ui.main.home.card.adapter.PlubCardAdapter
import com.plub.presentation.ui.main.home.search.adapter.RecentSearchAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchingFragment : BaseFragment<FragmentSearchingBinding, SearchingPageState, SearchingViewModel>(
    FragmentSearchingBinding::inflate
) {

    companion object {
        private const val POSITION_TOP = 0
        private const val TOTAL_SPAN_SIZE = 2
        private const val ITEM_SPAN_SIZE = 1
        private const val ITEM_VERTICAL_SPACE = 8
        private const val ITEM_HORIZONTAL_SPACE = 12
    }

    override val viewModel: SearchingViewModel by viewModels()

    private val recentListAdapter: RecentSearchAdapter by lazy {
        RecentSearchAdapter(object : RecentSearchAdapter.Delegate {
            override fun onClickDelete(text: String) {
                viewModel.onDeleteRecentSearch(text)
            }

            override fun onClickRecentSearch(text: String) {
                viewModel.onClickRecentSearch(text)
            }
        })
    }

    private val searchListAdapter: PlubCardAdapter by lazy {
        PlubCardAdapter(object : PlubCardAdapter.Delegate {
            override fun onClickBookmark(id: Int) {
                viewModel.onClickBookmark(id)
            }

            override fun onClickPlubCard(id: Int, isHost: Boolean) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            initTab()

            recyclerViewRecentSearch.apply {
                layoutManager = GridLayoutManager(context, TOTAL_SPAN_SIZE).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return ITEM_SPAN_SIZE
                        }
                    }
                }
                addItemDecoration(
                    GridSpaceDecoration(
                        TOTAL_SPAN_SIZE,
                        ITEM_HORIZONTAL_SPACE.px,
                        ITEM_VERTICAL_SPACE.px,
                        false
                    )
                )
                adapter = recentListAdapter
            }

            recyclerViewSearch.apply {
                layoutManager = GridLayoutManager(context, PlubCardType.TOTAL_SPAN_SIZE).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = searchListAdapter.getItemViewType(position)
                            val cardType = PlubCardType.valueOf(viewType)
                            return cardType.spanSize
                        }
                    }
                }

                adapter = searchListAdapter
            }

            editTextSearch.apply {
                setOnEditorActionListener { _, keyCode, keyEvent ->
                    if(keyCode == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.onSearchPlubRecruit(text.toString())
                        true
                    }else false
                }
            }
        }

        viewModel.fetchRecentSearchList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    recentListAdapter.submitList(it.recentSearchList)
                    searchListAdapter.submitList(it.searchList)
                    setSortTypeText(it.sortType)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as SearchingEvent)
                }
            }
        }
    }

    private fun initTab() {
        binding.tabLayoutSearchType.apply {
            PlubSearchType.values().forEach {
                addTab(getTabView(it))
            }
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewModel.onTabSelected(tab.position)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
                override fun onTabUnselected(tab: TabLayout.Tab) {}
            })
        }
    }

    private fun getTabView(searchType: PlubSearchType): TabLayout.Tab {
        val tab = binding.tabLayoutSearchType.newTab()
        return tab.apply {
            customView = IncludeTabSearchResultBinding.inflate(layoutInflater).apply {
                textViewTab.text = getTabTitle(searchType)
            }.root
        }
    }

    private fun getTabTitle(searchType: PlubSearchType): String {
        return when (searchType) {
            PlubSearchType.TITLE -> getString(R.string.search_result_search_type_title)
            PlubSearchType.NAME -> getString(R.string.search_result_search_type_name)
            PlubSearchType.TITLE_AND_CONTENTS -> getString(R.string.search_result_search_type_title_and_content)
        }
    }

    private fun inspectEventFlow(event: SearchingEvent) {
        when (event) {
            is SearchingEvent.ClearFocus -> {
                binding.editTextSearch.clearFocus()
            }
            is SearchingEvent.HideKeyboard -> hideKeyboard()
            is SearchingEvent.ScrollToTop -> scrollToTop()
            is SearchingEvent.ShowSelectSortTypeBottomSheetDialog -> showSelectSortTypeDialog(event.selectedItem)
        }
    }

    private fun setSortTypeText(sortType: PlubSortType) {
        val sortTypeRes = when (sortType) {
            PlubSortType.POPULAR -> R.string.word_sort_type_popular
            PlubSortType.NEW -> R.string.word_sort_type_new
        }
        binding.textViewSortType.text = getString(sortTypeRes)
    }

    private fun scrollToTop() {
        binding.recyclerViewSearch.scrollToPosition(POSITION_TOP)
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)
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
}