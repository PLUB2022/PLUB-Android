package com.plub.presentation.ui.main.profile.viewHolder.detail.recruiting

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitDetailVo.host.AccountsVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemApplicationBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailApplicationAnswerAdapter
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailPageAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick
import com.plub.presentation.util.px

class MyPageDetailOtherApplicantsViewHolder(
    private val binding: IncludeItemApplicationBinding,
    private val listener : MyPageDetailPageAdapter.ApplicantsDelegate
) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        const val VERTICAL_SPACE = 16
    }

    private val myPageDetailPageAdapter : MyPageDetailApplicationAnswerAdapter by lazy {
        MyPageDetailApplicationAnswerAdapter()
    }

    var isExpand = false
    var vo : AccountsVo? = null

    init {
        binding.apply {
            initRecycler()
            imageViewArrow.onThrottleClick {
                constraintLayoutRecycler.visibility = if(isExpand) View.GONE else View.VISIBLE
                isExpand = !isExpand
            }

            buttonApprove.onThrottleClick {
                vo?.let { listener.onClickApproveButton(it.accountId) }
            }

            buttonReject.onThrottleClick {
                vo?.let { listener.onClickRejectButton(it.accountId) }
            }
        }
    }

    private fun initRecycler(){
        binding.apply {
            recyclerViewApplicationContent.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(VERTICAL_SPACE.px))
                adapter = myPageDetailPageAdapter
            }
        }
    }

    fun bind(item: AccountsVo) {
        vo = item
        binding.apply {
            GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
            imageViewProfile.clipToOutline = true
            textViewName.text = item.accountName
            textViewDate.text = root.context.getString(R.string.my_page_complete_answer, item.createdAt)
            myPageDetailPageAdapter.submitList(item.answers)
        }
    }
}