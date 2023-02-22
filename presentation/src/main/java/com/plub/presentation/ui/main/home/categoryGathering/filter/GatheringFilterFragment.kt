package com.plub.presentation.ui.main.home.categoryGathering.filter

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            is GatheringFilterEvent.ClickDay -> {
                updateDayButton(event.day, event.isClick)
            }
            is GatheringFilterEvent.GoToCategoryGathering -> {
                goToCategoryGathering(event.pageState)
            }
            is GatheringFilterEvent.GoToBack -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun updateDayButton(day : DaysType, isClick : Boolean){
        binding.apply {
            when (day) {
                DaysType.ALL -> {
                    if (isClick) {
                        setButtonAllCheck()
                        checkedAllDayButton()
                    }
                    else{ uncheckedAllDayButton() }
                }
                DaysType.MON -> {
                    if (isClick) { checkedMondayButton() }
                    else{ uncheckedMondayButton() }
                }
                DaysType.TUE -> {
                    if (isClick) { checkedTuesdayButton() }
                    else{ uncheckedTuesdayButton() }
                }
                DaysType.WED -> {
                    if (isClick) { checkedWednesdayButton() }
                    else{ uncheckedWednesdayButton() }
                }
                DaysType.THR -> {
                    if (isClick) { checkedThursdayButton() }
                    else{ uncheckedThursdayButton() }
                }
                DaysType.FRI -> {
                    if (isClick) { checkedFridayButton() }
                    else{ uncheckedFridayButton() }
                }
                DaysType.SAT -> {
                    if (isClick) { checkedSaturdayButton() }
                    else{ uncheckedSaturdayButton() }
                }
                DaysType.SUN -> {
                    if (isClick) { checkedSundayButton() }
                    else{ uncheckedSundayButton() }
                }
            }
        }
    }

    private fun checkedAllDayButton(){
        binding.apply {
            buttonAllDay.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonAllDay.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedAllDayButton(){
        binding.apply {
            buttonAllDay.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonAllDay.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun checkedMondayButton(){
        binding.apply {
            buttonMonday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonMonday.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedMondayButton(){
        binding.apply {
            buttonMonday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonMonday.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun checkedTuesdayButton(){
        binding.apply {
            buttonTuesday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonTuesday.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedTuesdayButton(){
        binding.apply {
            buttonTuesday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonTuesday.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun checkedWednesdayButton(){
        binding.apply {
            buttonWednesday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonWednesday.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedWednesdayButton(){
        binding.apply {
            buttonWednesday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonWednesday.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun checkedThursdayButton(){
        binding.apply {
            buttonThursday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonThursday.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedThursdayButton(){
        binding.apply {
            buttonThursday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonThursday.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun checkedFridayButton(){
        binding.apply {
            buttonFriday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonFriday.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedFridayButton(){
        binding.apply {
            buttonFriday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonFriday.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun checkedSaturdayButton(){
        binding.apply {
            buttonSaturday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonSaturday.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedSaturdayButton(){
        binding.apply {
            buttonSaturday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonSaturday.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun checkedSundayButton(){
        binding.apply {
            buttonSunday.setBackgroundResource(R.drawable.bg_rectangle_filled_5f5ff9_radius_8)
            buttonSunday.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun uncheckedSundayButton(){
        binding.apply {
            buttonSunday.setBackgroundResource(R.drawable.bg_rectangle_empty_8c8c8c_radius_8)
            buttonSunday.setTextColor(resources.getColor(R.color.color_8c8c8c))
        }
    }

    private fun setButtonAllCheck(){
        checkedMondayButton()
        checkedTuesdayButton()
        checkedWednesdayButton()
        checkedThursdayButton()
        checkedFridayButton()
        checkedSaturdayButton()
        checkedSundayButton()
    }

    private fun goToCategoryGathering(pageState: GatheringFilterState){
        val action = GatheringFilterFragmentDirections.actionFilterToCategoryGathering(
            categoryId = gatheringFilterFragmentArgs.categoryId,
            categoryName = gatheringFilterFragmentArgs.categoryName,
            filter = pageState
        )
        findNavController().navigate(action)
    }
}