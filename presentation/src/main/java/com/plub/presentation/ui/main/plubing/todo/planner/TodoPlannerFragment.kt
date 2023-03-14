package com.plub.presentation.ui.main.plubing.todo.planner

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentTodoPlannerBinding
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.ui.common.decoration.CalendarDotDecorator
import com.plub.presentation.ui.common.decoration.CalendarTodayDecorator
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.common.dialog.todo.TodoCheckProofDialog
import com.plub.presentation.ui.main.plubing.board.detail.BoardDetailFragmentArgs
import com.plub.presentation.ui.main.plubing.todo.adapter.TodoItemAdapter
import com.plub.presentation.util.hideKeyboard
import com.plub.presentation.util.showKeyboard
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class TodoPlannerFragment :
    BaseTestFragment<FragmentTodoPlannerBinding, TodoPlannerPageState, TodoPlannerViewModel>(
        FragmentTodoPlannerBinding::inflate
    ) {

    private val todoPlannerArgs: TodoPlannerFragmentArgs by navArgs()

    private val todoListAdapter: TodoItemAdapter by lazy {
        TodoItemAdapter(object : TodoItemAdapter.Delegate {
            override fun onClickTodoCheck(todoItemVo: TodoItemVo) {
                viewModel.onClickTodoCheck(todoItemVo)
            }

            override fun onClickTodoMenu(todoItemVo: TodoItemVo) {
                viewModel.onClickTodoMenu(todoItemVo)
            }
        })
    }

    private val dotDecoration = CalendarDotDecorator()

    override val viewModel: TodoPlannerViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewTodo.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = todoListAdapter
            }

            editTextTodo.apply {
                setOnEditorActionListener { _, keyCode, keyEvent ->
                    if(keyCode == EditorInfo.IME_ACTION_DONE) {
                        viewModel.onClickTodoWrite(text.toString())
                        true
                    }else false
                }
            }

            calendarView.apply {
                topbarVisible = false
                val drawable = ContextCompat.getDrawable(context, R.drawable.bg_circle_empty_5f5ff9)
                addDecorator(CalendarTodayDecorator(drawable))
                setOnDateChangedListener { widget, date, selected ->
                    viewModel.onSelectedCalendarDay(date)
                }
                setOnMonthChangedListener { widget, date ->
                    viewModel.onChangeCalendarMonth(date)
                }
                addDecorator(dotDecoration)
            }
        }

        viewModel.initDate(todoPlannerArgs.todoDate)
        viewModel.onUpdatePlubName()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todoDays.collect {
                    updateCalendarDot(it)
                }
            }

            launch {
                viewModel.uiState.todoList.collect {
                    todoListAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as TodoPlannerEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: TodoPlannerEvent) {
        when (event) {
            is TodoPlannerEvent.SetCalendarCurrentDate -> setCurrentDate(event.calendarDay)
            is TodoPlannerEvent.CalendarMonthNext -> binding.calendarView.goToNext()
            is TodoPlannerEvent.CalendarMonthPrevious -> binding.calendarView.goToPrevious()
            is TodoPlannerEvent.ClearTodoEditText -> binding.editTextTodo.setText("")
            is TodoPlannerEvent.HideKeyboard -> binding.editTextTodo.hideKeyboard()
            is TodoPlannerEvent.ShowKeyboard -> {
                binding.root.postDelayed({
                    binding.editTextTodo.showKeyboard()
                },100)
            }
            is TodoPlannerEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.menuType, event.todoVo)
            is TodoPlannerEvent.ShowTodoProofDialog -> showTodoProofDialog(event.parseTodoItemVo)
        }
    }

    private fun setCurrentDate(date:CalendarDay) {
        binding.calendarView.currentDate = date
        binding.calendarView.setDateSelected(date, true)
    }

    private fun updateCalendarDot(days: List<Int>) {
        dotDecoration.setDays(days)
        binding.calendarView.removeDecorator(dotDecoration)
        binding.calendarView.addDecorator(dotDecoration)
    }

    private fun showMenuBottomSheetDialog(menuType: DialogMenuType, todoItemVo: TodoItemVo) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, todoItemVo)
        }.show(parentFragmentManager, "")
    }

    private fun showTodoProofDialog(parseTodoItemVo: ParseTodoItemVo) {
        TodoCheckProofDialog.newInstance(parseTodoItemVo, object: TodoCheckProofDialog.Delegate {
            override fun onClickComplete(todoId: Int, proofFile: File) {
                viewModel.onClickProofComplete(todoId, proofFile)
            }

            override fun onClickLateProof(todoId: Int) {}
        }).show(parentFragmentManager, "")
    }
}