package com.plub.presentation.ui.main.home.registhobbies

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentRegisterHobbiesBinding
import com.plub.presentation.ui.Event
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.sign.hobbies.HobbiesEvent
import com.plub.presentation.ui.sign.hobbies.HobbiesPageState
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterHobbiesFragment : BaseFragment<FragmentRegisterHobbiesBinding, HobbiesPageState, RegisterHobbiesViewModel>(
    FragmentRegisterHobbiesBinding::inflate
)  {
    companion object {
        private const val ITEM_VERTICAL_SPACE = 8
    }

    override val viewModel: RegisterHobbiesViewModel by viewModels()

    private val listAdapter: HobbiesAdapter by lazy {
        HobbiesAdapter(object : HobbiesAdapter.Delegate {
            override val selectedList: List<SelectedHobbyVo>
                get() = viewModel.uiState.value.hobbiesSelectedVo.hobbies

            override fun onClickExpand(hobbyId: Int) {
                viewModel.onClickExpand(hobbyId)
            }

            override fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
                viewModel.onClickSubHobby(isClicked, selectedHobbyVo)
            }

            override fun onClickLatePick() {

            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerViewInterestsCategory.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = listAdapter
                addItemDecoration(VerticalSpaceDecoration(ITEM_VERTICAL_SPACE.px))
            }
        }
        viewModel.fetchHobbiesData()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it)
                }
            }

            launch {
                viewModel.uiState.collect {
                    listAdapter.submitList(it.hobbiesVo)
                }
            }
        }
    }

    private fun inspectEventFlow(event: Event) {
        when(event) {
            is HobbiesEvent.NotifyAllHobby -> {
                listAdapter.notifyAllItemUpdate()
            }
            is HobbiesEvent.NotifySubHobby -> {
                listAdapter.notifySubItemUpdate(event.vo)
            }
            is RegisterHobbiesEvent.BackPage->{
                findNavController().popBackStack()
            }
        }
    }
}