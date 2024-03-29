package com.plub.presentation.ui.main.profile.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMyGatheringDetailBinding
import com.plub.presentation.ui.main.profile.adapter.MyPageParentGatheringAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.onThrottleClick

class MyPageGatheringViewHolder(
    private val binding: IncludeItemMyGatheringDetailBinding,
    private val listener: MyPageParentGatheringAdapter.MyPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    var myPageGatheringDetailVo : MyPageGatheringDetailVo? = null

    init {
        binding.root.onThrottleClick {
            myPageGatheringDetailVo?.let { listener.onClickGathering(it.gatheringParentType, it.gatheringType, it.plubbingId) }
        }
    }

    fun bind(item: MyPageGatheringDetailVo) {
        myPageGatheringDetailVo = item
        binding.apply {
            constraintLayoutGatheringStateBox.visibility = View.GONE
            GlideUtil.loadImage(root.context, item.image, imageViewGatheringIcon)
            textViewGatheringName.text = item.title
            textViewGatheringGoal.text = item.goal

            showMyType(item)
        }
    }

    private fun showMyType(item : MyPageGatheringDetailVo){
        binding.apply {
            if(item.gatheringParentType == MyPageGatheringStateType.ACTIVE || item.gatheringParentType == MyPageGatheringStateType.END){
                constraintLayoutGatheringStateBox.visibility = View.VISIBLE
                when(item.gatheringType){
                    MyPageGatheringMyType.HOST -> {
                        constraintLayoutGatheringStateBox.setBackgroundResource(R.drawable.bg_rectangle_filled_feefea_radius_6)
                        textViewGatheringState.text = root.context.getString(R.string.my_page_gathering_host)
                        textViewGatheringState.setTextColor(root.context.getColor(R.color.color_f75b2b))
                    }
                    MyPageGatheringMyType.GUEST -> {
                        constraintLayoutGatheringStateBox.setBackgroundResource(R.drawable.bg_rectangle_filled_e1e1fa_radius_6)
                        textViewGatheringState.text = root.context.getString(R.string.my_page_gathering_guest)
                        textViewGatheringState.setTextColor(root.context.getColor(R.color.color_5f5ff9))
                    }
                    MyPageGatheringMyType.END -> {
                        constraintLayoutGatheringStateBox.setBackgroundResource(R.drawable.bg_rectangle_filled_f2f3f4_radius_6)
                        textViewGatheringState.text = root.context.getString(R.string.my_page_gathering_end)
                        textViewGatheringState.setTextColor(root.context.getColor(R.color.color_8c8c8c))
                    }
                    MyPageGatheringMyType.EXIT -> {
                        constraintLayoutGatheringStateBox.setBackgroundResource(R.drawable.bg_rectangle_filled_f2f3f4_radius_6)
                        textViewGatheringState.text = root.context.getString(R.string.my_page_gathering_exit)
                        textViewGatheringState.setTextColor(root.context.getColor(R.color.color_8c8c8c))
                    }
                }
            }
        }
    }
}