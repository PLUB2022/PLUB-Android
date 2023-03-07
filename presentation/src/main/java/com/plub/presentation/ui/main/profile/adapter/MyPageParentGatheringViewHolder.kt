package com.plub.presentation.ui.main.profile.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMyGatheringBinding
import com.plub.presentation.util.animation.ArrowToggleAnimation

class MyPageParentGatheringViewHolder(
    private val binding: IncludeItemMyGatheringBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val detailAdapter : MyPageGatheringAdapter by lazy {
        MyPageGatheringAdapter()
    }

    var isExpand = false

    init {
        binding.apply {
            imageViewArrow.setOnClickListener {
                ArrowToggleAnimation.toggleArrow(it, isExpand)
                if(isExpand){
                    recyclerViewGatheringList.visibility = View.GONE
                }
                else{
                    recyclerViewGatheringList.visibility = View.VISIBLE
                }
                isExpand = !isExpand
            }
        }
    }

    fun bind(item: MyPageGatheringVo) {
        binding.apply {
            when(item.gatheringType){
                0 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_recruiting_gathering)}
                1 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_waiting_gathering)}
                2 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_active_gathering)}
                3 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_end_gathering)}
            }
            detailAdapter.submitList(item.gatheringList)
            recyclerViewGatheringList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = detailAdapter
            }
        }
    }
}