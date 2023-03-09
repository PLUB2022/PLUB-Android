package com.plub.presentation.ui.main.home.profile.viewHolder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMyGatheringBinding
import com.plub.presentation.ui.main.home.profile.adapter.MyPageGatheringAdapter
import com.plub.presentation.ui.main.home.profile.adapter.MyPageParentGatheringAdapter
import com.plub.presentation.util.animation.ArrowToggleAnimation

class MyPageParentGatheringViewHolder(
    private val binding: IncludeItemMyGatheringBinding,
    private val listener: MyPageParentGatheringAdapter.MyPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private val detailAdapter : MyPageGatheringAdapter by lazy {
        MyPageGatheringAdapter(listener)
    }

    var myPageGatheringVo : MyPageGatheringVo? = null

    init {
        binding.apply {
            recyclerViewGatheringList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = detailAdapter
            }
            imageViewArrow.setOnClickListener {
                myPageGatheringVo?.let {
                    ArrowToggleAnimation.toggleArrow(imageViewArrow, it.isExpand)
                    listener.onClickCardExpand(it.gatheringType)
                }
            }
        }
    }

    fun bind(item: MyPageGatheringVo) {
        myPageGatheringVo = item
        binding.apply {
            when(item.gatheringType){
                MyPageGatheringType.RECRUITING -> {textViewGatheringType.text = root.context.getString(R.string.my_page_recruiting_gathering)}
                MyPageGatheringType.WAITING  -> {textViewGatheringType.text = root.context.getString(R.string.my_page_waiting_gathering)}
                MyPageGatheringType.ACTIVE  -> {textViewGatheringType.text = root.context.getString(R.string.my_page_active_gathering)}
                MyPageGatheringType.END  -> {textViewGatheringType.text = root.context.getString(R.string.my_page_end_gathering)}
            }
            detailAdapter.submitList(item.gatheringList)
            recyclerViewGatheringList.visibility = if (item.isExpand) View.VISIBLE else View.GONE
        }
    }
}