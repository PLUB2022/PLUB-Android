package com.plub.presentation.ui.home.search

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.plub.domain.model.enums.PlubSearchType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSearchingBinding
import com.plub.presentation.event.SearchingEvent
import com.plub.presentation.ui.common.GridSpaceDecoration
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceFragmentDirections
import com.plub.presentation.ui.home.search.adapter.RecentSearchAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchingFragment : BaseFragment<FragmentSearchingBinding, SearchingPageState, SearchingViewModel>(
    FragmentSearchingBinding::inflate
) {

    companion object {
        private const val TOTAL_SPAN_SIZE = 2
        private const val ITEM_SPAN_SIZE = 1
        private const val ITEM_VERTICAL_SPACE = 8
        private const val ITEM_HORIZONTAL_SPACE = 12
    }

    override val viewModel: SearchingViewModel by viewModels()

    private val listAdapter: RecentSearchAdapter by lazy {
        RecentSearchAdapter(object : RecentSearchAdapter.Delegate {
            override fun onClickDelete(id: Int) {
                viewModel.onDeleteRecentSearch(id)
            }

            override fun onClickRecentSearch(text: String) {
                viewModel.onRecentSearch(text)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

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
                adapter = listAdapter
            }

            editTextSearch.apply {
                setOnEditorActionListener { _, keyCode, keyEvent ->
                    if(keyCode == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.onSearch(text.toString())
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
                    listAdapter.submitList(it.recentSearchList)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as SearchingEvent)
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)
    }

    private fun clear() {
        binding.editTextSearch.setText("")
        binding.editTextSearch.clearFocus()
    }

    private fun goToSearchResult(search:String) {
        val action = SearchingFragmentDirections.actionSearchingToSearchResult(search)
        findNavController().navigate(action)
    }

    private fun inspectEventFlow(event: SearchingEvent) {
        when (event) {
            is SearchingEvent.GoToSearchResult -> goToSearchResult(event.search)
            is SearchingEvent.HideKeyboard -> hideKeyboard()
            is SearchingEvent.Clear -> clear()
        }
    }
}