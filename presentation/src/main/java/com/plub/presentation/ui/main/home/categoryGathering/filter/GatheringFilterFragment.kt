package com.plub.presentation.ui.main.home.categoryGathering.filter

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentCategoryGatheringFilterBinding
import com.plub.presentation.parcelableVo.ParseCategoryFilterVo
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter
import com.plub.presentation.ui.sign.hobbies.adapter.SubHobbiesAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GatheringFilterFragment :
    BaseTestFragment<FragmentCategoryGatheringFilterBinding, GatheringFilterState, GatheringFilterViewModel>(
        FragmentCategoryGatheringFilterBinding::inflate
    ) {

    companion object{
        const val ITEM_SPAN_SIZE = 4
        const val HORIZONTAL_SPACE = 12
        const val VERTICAL_SPACE = 8
    }

    private val listAdapter: SubHobbiesAdapter by lazy {
        SubHobbiesAdapter(object : HobbiesAdapter.Delegate {
            override val selectedList: List<SelectedHobbyVo>
                get() = viewModel.uiState.selectedHobbies.value

            override fun onClickExpand(hobbyId: Int) {

            }

            override fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
                viewModel.onClickSubHobby(isClicked, selectedHobbyVo)
            }

            override fun onClickLatePick() {

            }
        })
    }

    private val gatheringFilterFragmentArgs : GatheringFilterFragmentArgs by navArgs()
    override val viewModel: GatheringFilterViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            initRecycler()
        }
        viewModel.fetchSubHobbies(gatheringFilterFragmentArgs.categoryId, gatheringFilterFragmentArgs.categoryName)
    }

    private fun initRecycler(){
        binding.apply {
            recyclerViewSubHobbies.apply {
                layoutManager = GridLayoutManager(context, ITEM_SPAN_SIZE)
                addItemDecoration(GridSpaceDecoration(ITEM_SPAN_SIZE, HORIZONTAL_SPACE.px, VERTICAL_SPACE.px, false))
                adapter = listAdapter
            }
        }
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.subHobbies.collect {
                    listAdapter.submitList(it)
                    viewModel.updateButtonState()
                }
            }

            launch {
                viewModel.uiState.gatheringDays.collect{
                    viewModel.updateButtonState()
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as GatheringFilterEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: GatheringFilterEvent) {
        when(event) {
            is GatheringFilterEvent.NotifySubHobby -> {
                listAdapter.updateOnClick(event.vo.subId)
            }
            is GatheringFilterEvent.GoToCategoryGathering -> {
                goToCategoryGathering(event.pageState)
            }
            is GatheringFilterEvent.GoToBack -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun goToCategoryGathering(vo: ParseCategoryFilterVo){
        val action = GatheringFilterFragmentDirections.actionFilterToCategoryGathering(
            categoryId = gatheringFilterFragmentArgs.categoryId,
            categoryName = gatheringFilterFragmentArgs.categoryName,
            filter = vo
        )
        findNavController().navigate(action)
    }
}