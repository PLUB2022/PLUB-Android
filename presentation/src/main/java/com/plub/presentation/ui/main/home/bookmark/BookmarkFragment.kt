package com.plub.presentation.ui.main.home.bookmark

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.plub.domain.model.enums.PlubCardType
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentBookmarkBinding
import com.plub.presentation.ui.main.home.card.adapter.PlubCardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment :
    BaseTestFragment<FragmentBookmarkBinding, BookmarkPageState, BookmarkViewModel>(
        FragmentBookmarkBinding::inflate
    ) {

    companion object {
        private const val POSITION_TOP = 0
    }

    override val viewModel: BookmarkViewModel by viewModels()

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

            recyclerViewBookmark.apply {
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
        }

        viewModel.onGetPlubBookmark()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.bookmarkList.collect {
                    listAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as BookmarksEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: BookmarksEvent) {
        when(event) {
            is BookmarksEvent.ScrollToTop -> scrollToTop()
            is BookmarksEvent.GoToBack -> findNavController().popBackStack()
        }
    }

    private fun scrollToTop() {
        binding.recyclerViewBookmark.scrollToPosition(POSITION_TOP)
    }
}