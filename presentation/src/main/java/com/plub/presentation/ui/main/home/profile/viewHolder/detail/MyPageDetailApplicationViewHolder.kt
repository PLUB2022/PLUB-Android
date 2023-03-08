package com.plub.presentation.ui.main.home.profile.viewHolder.detail

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageApplicationsVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemApplicationBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.home.profile.adapter.MyPageDetailApplicationAnswerAdapter
import com.plub.presentation.ui.main.home.profile.adapter.MyPageDetailPageAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.px

class MyPageDetailApplicationViewHolder(
    private val binding: IncludeItemApplicationBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        const val VERTICAL_SPACE = 16
    }

    private val myPageDetailPageAdapter : MyPageDetailApplicationAnswerAdapter by lazy {
        MyPageDetailApplicationAnswerAdapter()
    }

    var isExpand = false

    init {
        binding.apply {
            imageViewArrow.setOnClickListener {
                if(isExpand){
                    constraintLayoutRecycler.visibility = View.GONE
                }
                else{
                    constraintLayoutRecycler.visibility = View.VISIBLE
                }
                isExpand = !isExpand
            }

            recyclerViewApplicationContent.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(VERTICAL_SPACE.px))
                adapter = myPageDetailPageAdapter
            }
        }
    }

    fun bind(item: MyPageApplicationsVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
            imageViewProfile.clipToOutline = true
            textViewName.text = item.name
            textViewDate.text = root.context.getString(R.string.my_page_complete_answer, item.date)
            myPageDetailPageAdapter.submitList(item.answerList)
        }
    }
}