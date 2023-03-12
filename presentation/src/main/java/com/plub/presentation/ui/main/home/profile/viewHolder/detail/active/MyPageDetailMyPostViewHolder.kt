package com.plub.presentation.ui.main.home.profile.viewHolder.detail.active

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.board.PlubingBoardListVo
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.databinding.IncludeItemMyPageActiveMyPostBinding
import com.plub.presentation.ui.main.home.profile.active.adapter.ActiveGatheringParentAdapter
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter

class MyPageDetailMyPostViewHolder(
    private val binding: IncludeItemMyPageActiveMyPostBinding,
    private val listener : ActiveGatheringParentAdapter.ActiveGatheringDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private val boardListAdapter: PlubingBoardAdapter by lazy {
        PlubingBoardAdapter(object : PlubingBoardAdapter.Delegate {
            override fun onClickClipBoard() {

            }

            override fun onClickBoard(feedId: Int) {
                listener.onClickBoard(feedId)
            }

            override fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
            }

            override val clipBoardList: List<PlubingBoardVo>
                get() = emptyList()
        })
    }

    init {
        binding.apply {
            recyclerViewPostList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = boardListAdapter
            }
        }
    }

    fun bind(item : List<PlubingBoardVo>) {
        if (item.isEmpty()) showEmpty()
        boardListAdapter.submitList(item)
    }

    private fun showEmpty(){
        binding.apply {
            textViewSeeAll.visibility = View.GONE
            constraintLayoutEmptyPost.visibility = View.VISIBLE
            recyclerViewPostList.visibility = View.GONE
        }
    }
}