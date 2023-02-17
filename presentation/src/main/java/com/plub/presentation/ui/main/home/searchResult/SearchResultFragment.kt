package com.plub.presentation.ui.main.home.searchResult

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.enums.PlubSortType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSearchResultBinding
import com.plub.presentation.databinding.IncludeTabSearchResultBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.adapter.DialogMenuAdapter
import com.plub.presentation.ui.main.home.card.adapter.PlubCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding, SearchResultPageState, SearchResultViewModel>(
        FragmentSearchResultBinding::inflate
    ) {

    companion object {
        private const val POSITION_TOP = 0
    }

    override val viewModel: SearchResultViewModel by viewModels()
    private val searchArgs: SearchResultFragmentArgs by navArgs()

    private val listAdapter: PlubCardAdapter by lazy {
        PlubCardAdapter(object : PlubCardAdapter.Delegate {
            override fun onClickBookmark(id: Int) {
                viewModel.onClickBookmark(id)
            }

            override fun onClickPlubCard(id: Int, isHost : Boolean) {

            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            initTab()

            recyclerViewSearch.apply {
                layoutManager = GridLayoutManager(context, PlubCardType.TOTAL_SPAN_SIZE).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val viewType = listAdapter.getItemViewType(position)
                            val cardType = PlubCardType.valueOf(viewType)
                            return cardType.spanSize
                        }
                    }
                }

                adapter = listAdapter
            }

            editTextSearch.apply {
                setText(searchArgs.search)
                setOnEditorActionListener { _, keyCode, keyEvent ->
                    if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.onSearchPlubRecruit(text.toString())
                        true
                    } else false
                }
            }
        }
        viewModel.onSearchPlubRecruit(searchArgs.search)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    listAdapter.submitList(it.searchList)
                    setSortTypeText(it.sortType)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as SearchResultEvent)
                }
            }
        }
    }

    private fun initTab() {
        binding.tabLayoutSearchType.apply {
            PlubSearchType.values().forEach {
                addTab(getTabView(it))
            }
            addOnTabSelectedListener(object : OnTabSelectedListener {
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

    private fun setSortTypeText(sortType: PlubSortType) {
        val sortTypeRes = when (sortType) {
            PlubSortType.POPULAR -> R.string.word_sort_type_popular
            PlubSortType.NEW -> R.string.word_sort_type_new
        }
        binding.textViewSortType.text = getString(sortTypeRes)
    }

    private fun inspectEventFlow(event: SearchResultEvent) {
        when (event) {
            is SearchResultEvent.ScrollToTop -> scrollToTop()
            is SearchResultEvent.HideKeyboard -> hideKeyboard()
            is SearchResultEvent.ShowSelectSortTypeBottomSheetDialog -> showSelectSortTypeDialog(event.selectedItem)
            is SearchResultEvent.NavigationPopEvent -> findNavController().popBackStack()
        }
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