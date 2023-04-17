package com.plub.presentation.ui.main.profile.active.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.databinding.IncludeItemMyPageActiveMyTodoBinding
import com.plub.presentation.util.onThrottleClick

class MyPageDetailMyTodoViewHolder(
    private val binding: IncludeItemMyPageActiveMyTodoBinding,
    private val listener : ActiveGatheringParentAdapter.ActiveGatheringDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private val todoListAdapter: MyPageTodoTimeLineAdapter by lazy {
        MyPageTodoTimeLineAdapter(object : MyPageTodoTimeLineAdapter.MyPageTodoDelegate {

            override fun onClickTimeline(timelineId: Int) {
                listener.onClickTimeline(timelineId)
            }

            override fun onClickTodoChecked(timelineId:Int, vo: TodoItemVo) {
                listener.onClickTodoCheck(timelineId, vo)
            }

            override fun onClickTodoMenu(vo: TodoTimelineVo) {
                listener.onClickTodoMenu(vo)
            }

            override fun onClickTodoLike(timelineId: Int) {
                listener.onClickTodoLike(timelineId)
            }
        })
    }

    init {
        binding.apply {
            recyclerViewTodoList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = todoListAdapter
            }

            constraintLayoutEmptyTodo.onThrottleClick {
                listener.onClickEmptyTodo()
            }

            textViewSeeAll.onThrottleClick {
                listener.onClickAllMyTodo()
            }
        }
    }

    fun bind(item : List<TodoTimelineVo>) {
        if (item.isEmpty()) showEmpty()
        todoListAdapter.submitList(item)
    }

    private fun showEmpty(){
        binding.apply {
            textViewSeeAll.visibility = View.GONE
            constraintLayoutEmptyTodo.visibility = View.VISIBLE
            recyclerViewTodoList.visibility = View.GONE
        }
    }
}