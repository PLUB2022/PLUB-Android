package com.plub.presentation.ui.main.home.profile.viewHolder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMyGatheringBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.home.profile.adapter.MyPageGatheringAdapter
import com.plub.presentation.ui.main.home.profile.adapter.MyPageParentGatheringAdapter
import com.plub.presentation.util.onThrottleClick
import com.plub.presentation.util.px

class MyPageParentGatheringViewHolder(
    private val binding: IncludeItemMyGatheringBinding,
    private val listener: MyPageParentGatheringAdapter.MyPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        const val VERTICAL_SPACE = 17
    }

    private val detailAdapter : MyPageGatheringAdapter by lazy {
        MyPageGatheringAdapter(listener)
    }

    var myPageGatheringVo : MyPageGatheringVo? = null

    init {
        binding.apply {
            recyclerViewGatheringList.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(VERTICAL_SPACE.px))
                adapter = detailAdapter
            }
            imageViewArrow.onThrottleClick {
                myPageGatheringVo?.let {
                    listener.onClickCardExpand(it.gatheringType)
                }
            }
        }
    }

    fun bind(item: MyPageGatheringVo) {
        myPageGatheringVo = item
        binding.apply {
            val textRes = when(item.gatheringType){
                MyPageGatheringStateType.RECRUITING -> R.string.my_page_recruiting_gathering
                MyPageGatheringStateType.WAITING  -> R.string.my_page_waiting_gathering
                MyPageGatheringStateType.ACTIVE  -> R.string.my_page_active_gathering
                MyPageGatheringStateType.END  -> R.string.my_page_end_gathering
            }
            textViewGatheringType.text = root.context.getString(textRes)
            val gatheringList = item.gatheringList.map {
                it.copy(
                    gatheringParentType = item.gatheringType
                )
            }
            detailAdapter.submitList(gatheringList)
            recyclerViewGatheringList.visibility = if (item.isExpand) View.VISIBLE else View.GONE
        }
    }
}