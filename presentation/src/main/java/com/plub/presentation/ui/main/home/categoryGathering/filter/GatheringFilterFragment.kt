package com.plub.presentation.ui.main.home.categoryGathering.filter

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCategoryGatheringFilterBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter
import com.plub.presentation.ui.sign.hobbies.adapter.SubHobbiesAdapter
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.px
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GatheringFilterFragment :
    BaseFragment<FragmentCategoryGatheringFilterBinding, GatheringFilterState, GatheringFilterViewModel>(
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
                get() = viewModel.uiState.value.hobbiesSelectedVo.hobbies

            override fun onClickExpand(hobbyId: Int) {

            }

            override fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
                viewModel.onClickSubHobby(isClicked, selectedHobbyVo)
                PlubLogger.logD(selectedList.toString())
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
                viewModel.uiState.collect {
                    listAdapter.submitList(it.subHobbies)
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
            is GatheringFilterEvent.ClickAllDay -> {

            }
            is GatheringFilterEvent.ClickDay -> {
                updateDayButton(event.day, event.isClick)
            }
        }
    }

    private fun updateDayButton(day : DaysType, isClick : Boolean){
        binding.apply {
            when (day) {
                DaysType.MON -> {
                    if (isClick) {
                        buttonMonday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonMonday.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonMonday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonMonday.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
                DaysType.TUE -> {
                    if (isClick) {
                        buttonTuesday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonTuesday.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonTuesday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonTuesday.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
                DaysType.WED -> {
                    if (isClick) {
                        buttonWednesday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonWednesday.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonWednesday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonWednesday.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
                DaysType.THR -> {
                    if (isClick) {
                        buttonThursday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonThursday.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonThursday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonThursday.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
                DaysType.FRI -> {
                    if (isClick) {
                        buttonFriday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonFriday.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonFriday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonFriday.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
                DaysType.SAT -> {
                    if (isClick) {
                        buttonSaturday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonSaturday.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonSaturday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonSaturday.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
                DaysType.SUN -> {
                    if (isClick) {
                        buttonSunday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonSunday.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonSunday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonSunday.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
                DaysType.ALL -> {
                    if (isClick) {
                        buttonAllDay.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
                        buttonAllDay.setTextColor(resources.getColor(R.color.white))
                    }
                    else{
                        buttonAllDay.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
                        buttonAllDay.setTextColor(resources.getColor(R.color.color_8c8c8c))
                    }
                }
            }
        }
    }
}