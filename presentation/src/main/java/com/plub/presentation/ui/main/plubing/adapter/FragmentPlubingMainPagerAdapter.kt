package com.plub.presentation.ui.main.plubing.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.presentation.ui.main.plubing.board.PlubingBoardFragment
import com.plub.presentation.ui.main.plubing.todo.PlubingTodoFragment

class FragmentPlubingMainPagerAdapter(
    fragment: Fragment,
    private val plubingId:Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = PlubingMainPageType.values().size

    override fun createFragment(position: Int): Fragment {
        return when (PlubingMainPageType.valueOf(position)) {
            PlubingMainPageType.BOARD -> PlubingBoardFragment.newInstance(plubingId)
            PlubingMainPageType.TODO_LIST -> PlubingTodoFragment.newInstance(plubingId)
        }
    }
}
