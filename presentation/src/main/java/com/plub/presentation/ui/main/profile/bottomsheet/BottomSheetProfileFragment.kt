package com.plub.presentation.ui.main.profile.bottomsheet

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetOtherProfileBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.profile.active.myTodo.MyPageAllMyTodoEvent
import com.plub.presentation.ui.main.profile.bottomsheet.adapter.OtherProfileAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomSheetProfileFragment :
    BaseBottomSheetFragment<BottomSheetOtherProfileBinding, BottomSheetOtherProfileState, BottomSheetProfileViewModel>(
        BottomSheetOtherProfileBinding::inflate
    ) {

    companion object {
        fun newInstance(
            nickName: String,
            accountId : Int,
            plubbingId : Int
        ) = BottomSheetProfileFragment().apply {
            this.nickName = nickName
            this.accountId = accountId
            this.plubbingId = plubbingId
        }
    }

    private var nickName: String = ""
    private var accountId: Int = -1
    private var plubbingId: Int = -1

    private val otherProfileAdapter : OtherProfileAdapter by lazy {
        OtherProfileAdapter(object : OtherProfileAdapter.TodoDelegate{
            override fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo) {

            }

            override fun onClickTodoMenu(vo: TodoTimelineVo) {
                //viewModel.onClickTodoMenu(vo)
            }

            override fun onClickTodoLike(timelineId: Int) {
                //TODO("Not yet implemented")
            }

            override fun onClickTimeline(timelineId: Int) {

            }
        })
    }

    override val viewModel: BottomSheetProfileViewModel by viewModels()

    override fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            recyclerViewProfileList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = otherProfileAdapter
            }
        }

        viewModel.fetchOtherProfile(nickName, accountId, plubbingId)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect{
                    otherProfileAdapter.submitList(it.dataList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as BottomSheetProfileEvent)
                }
            }
        }
    }

    private fun inspectEvent(event : BottomSheetProfileEvent){
        when(event){
            is BottomSheetProfileEvent.ShowMenuBottomSheetDialog -> showMenuBottomSheetDialog(event.todoTimelineVo, event.menuType)
        }
    }

    private fun showMenuBottomSheetDialog(todoTimelineVo: TodoTimelineVo, menuType: DialogMenuType) {
        SelectMenuBottomSheetDialog.newInstance(menuType) {
            viewModel.onClickMenuItemType(it, todoTimelineVo)
        }.show(parentFragmentManager, "")
    }
}