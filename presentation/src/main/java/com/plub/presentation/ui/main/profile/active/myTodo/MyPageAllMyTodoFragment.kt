package com.plub.presentation.ui.main.profile.active.myTodo

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageAllMyTodoBinding
import com.plub.presentation.ui.main.profile.active.adapter.MyPageTodoTimeLineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageAllMyTodoFragment :
    BaseFragment<FragmentMyPageAllMyTodoBinding, MyPageAllMyTodoState, MyPageAllMyTodoViewModel>(
        FragmentMyPageAllMyTodoBinding::inflate
    ) {

    override val viewModel: MyPageAllMyTodoViewModel by viewModels()

    private val myPageTodoTimeLineAdapter: MyPageTodoTimeLineAdapter by lazy {
        MyPageTodoTimeLineAdapter(object : MyPageTodoTimeLineAdapter.MyPageTodoDelegate {
            override fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo) {
                TODO("Not yet implemented")
            }

            override fun onClickTodoMenu(vo: TodoTimelineVo) {
                TODO("Not yet implemented")
            }

            override fun onClickTodoLike(timelineId: Int) {
                TODO("Not yet implemented")
            }

            override fun onClickTimeline(timelineId: Int) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewTodoList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = myPageTodoTimeLineAdapter
            }
        }

        viewModel.getMyToDoList()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    myPageTodoTimeLineAdapter.submitList(it.todoList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as MyPageAllMyTodoEvent)
                }
            }
        }
    }

    private fun inspectEvent(event : MyPageAllMyTodoEvent){
        when(event){
            is MyPageAllMyTodoEvent.GoToBack -> findNavController().popBackStack()
        }
    }
}