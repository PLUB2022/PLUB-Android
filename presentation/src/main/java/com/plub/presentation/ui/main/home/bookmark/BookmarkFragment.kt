package com.plub.presentation.ui.main.home.bookmark

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.plub.domain.model.enums.PlubCardType
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentBookmarkBinding
import com.plub.presentation.ui.main.home.card.adapter.PlubCardAdapter
import com.plub.presentation.util.infiniteScrolls
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

            override fun onClickPlubCard(id: Int) {
                viewModel.goToDetailRecruitment(id)
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

                infiniteScrolls { viewModel.onScrollChanged() }

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
            is BookmarksEvent.GoToRecruit -> goToDetailRecruitment(event.id)
        }
    }

    private fun goToDetailRecruitment(plubbingId: Int) {
        val action =
            BookmarkFragmentDirections.actionBookmarkToRecruitment(plubbingId)
        findNavController().navigate(action)
    }

    private fun scrollToTop() {
        binding.recyclerViewBookmark.scrollToPosition(POSITION_TOP)
    }
}