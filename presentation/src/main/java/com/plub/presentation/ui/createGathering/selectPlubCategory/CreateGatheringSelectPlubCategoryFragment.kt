package com.plub.presentation.ui.createGathering.selectPlubCategory

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringSelectPlubCategoryBinding
import com.plub.presentation.ui.common.VerticalSpaceDecoration
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter
import com.plub.presentation.util.dp
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreateGatheringSelectPlubCategoryFragment :
    BaseFragment<FragmentCreateGatheringSelectPlubCategoryBinding, CreateGatheringSelectPlubCategoryPageState, CreateGatheringSelectPlubCategoryViewModel>(
        FragmentCreateGatheringSelectPlubCategoryBinding::inflate
    ) {

    companion object {
        private const val ITEM_VERTICAL_SPACE = 16
    }

    override val viewModel: CreateGatheringSelectPlubCategoryViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

    private val listAdapter: HobbiesAdapter by lazy {
        HobbiesAdapter(object : HobbiesAdapter.Delegate {
            override val selectedList: List<SelectedHobbyVo>
                get() = viewModel.uiState.value.categoriesSelectedVo.hobbies

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
            parentVm = parentViewModel

            recyclerViewCategories.apply {
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
                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    viewModel.initUiState(pageState)
                }
            }

            launch {
                viewModel.uiState.collect {
                    listAdapter.submitList(it.categoriesVo)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    when (it) {
                        is CreateGatheringSelectPlubCategoryEvent.NotifySubHobby -> {
                            listAdapter.notifySubItemUpdate(it.selectedHobbyVo)
                        }

                        is CreateGatheringSelectPlubCategoryEvent.NotifyAllHobby -> {
                            listAdapter.notifyAllItemUpdate()
                        }
                    }
                }
            }
        }
    }
}