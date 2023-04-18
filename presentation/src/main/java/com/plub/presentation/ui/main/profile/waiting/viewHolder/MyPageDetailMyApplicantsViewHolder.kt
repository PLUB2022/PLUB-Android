package com.plub.presentation.ui.main.profile.waiting.viewHolder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitDetailVo.host.AccountsVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMyApplicationBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailApplicationAnswerAdapter
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailPageAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick
import com.plub.presentation.util.px

class MyPageDetailMyApplicantsViewHolder(
    private val binding: IncludeItemMyApplicationBinding,
    private val listener : MyPageDetailPageAdapter.ApplicantsDelegate
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
            imageViewArrow.onThrottleClick {
                constraintLayoutRecycler.visibility = if(isExpand) View.GONE else View.VISIBLE
                isExpand = !isExpand
            }

            recyclerViewApplicationContent.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(VERTICAL_SPACE.px))
                adapter = myPageDetailPageAdapter
            }

            buttonCancel.onThrottleClick {
                listener.onClickCancelButton()
            }

            buttonOnlyCancel.onThrottleClick {
                listener.onClickCancelButton()
            }

            buttonModify.onThrottleClick {
                listener.onClickModifyButton()
            }
        }
    }

    fun bind(item: AccountsVo) {
        binding.apply {
            if(item.profileImage.isNullOrEmpty()) imageViewProfile.setImageResource(R.drawable.iv_default_profile)
            else GlideUtil.loadImage(root.context, item.profileImage!!, imageViewProfile)
            textViewName.text = item.accountName
            textViewDate.text = root.context.getString(R.string.my_page_complete_answer, item.createdAt)

            if(item.answers.isEmpty()){
                buttonCancel.visibility = View.GONE
                buttonModify.visibility = View.GONE
                buttonOnlyCancel.visibility = View.VISIBLE
            }
            else{
                buttonCancel.visibility = View.VISIBLE
                buttonModify.visibility = View.VISIBLE
                buttonOnlyCancel.visibility = View.GONE
            }

            myPageDetailPageAdapter.submitList(item.answers)
        }
    }
}