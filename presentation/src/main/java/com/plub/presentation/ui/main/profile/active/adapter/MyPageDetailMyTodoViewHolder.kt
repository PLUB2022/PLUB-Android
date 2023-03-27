package com.plub.presentation.ui.main.profile.active.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.databinding.IncludeItemMyPageActiveMyTodoBinding

class MyPageDetailMyTodoViewHolder(
    private val binding: IncludeItemMyPageActiveMyTodoBinding,
) : RecyclerView.ViewHolder(binding.root) {

//    private val boardListAdapter: PlubingBoardAdapter by lazy {
//        PlubingBoardAdapter(object : PlubingBoardAdapter.Delegate {
//            override fun onClickClipBoard() {
//
//            }
//
//            override fun onClickBoard(feedId: Int) {
//                listener.onClickBoard(feedId)
//            }
//
//            override fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
//            }
//
//            override val clipBoardList: List<PlubingBoardVo>
//                get() = emptyList()
//        })
//    }

    init {
//        binding.apply {
//            recyclerViewPostList.apply {
//                layoutManager = LinearLayoutManager(context)
//                adapter = boardListAdapter
//            }
//        }
    }

    fun bind(item : List<TodoTimelineVo>) {
        if (item.isEmpty()) showEmpty()
        //boardListAdapter.submitList(item)
    }

    private fun showEmpty(){
        binding.apply {
            textViewSeeAll.visibility = View.GONE
            constraintLayoutEmptyTodo.visibility = View.VISIBLE
            recyclerViewTodoList.visibility = View.GONE
        }
    }
}