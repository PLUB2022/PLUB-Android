package com.plub.presentation.ui.main.profile.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageMyProfileVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeMyPageProfileBinding
import com.plub.presentation.ui.main.profile.MyPageFragment
import com.plub.presentation.ui.main.profile.adapter.MyPageParentGatheringAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class MyPageProfileViewHolder(
    private val binding: IncludeMyPageProfileBinding,
    private val listener: MyPageParentGatheringAdapter.MyPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val MAX_LENGTH = 15
    }

    private var isExpandText : Boolean = false

    init {
        binding.apply {
            textViewReadMore.onThrottleClick {
                if(isExpandText){
                    textViewReadMore.text = root.context.getString(R.string.my_page_close)
                    textViewProfileExplain.maxLines = Int.MAX_VALUE
                }
                else{
                    textViewReadMore.text = root.context.getString(R.string.my_page_read_more)
                    textViewProfileExplain.maxLines = MyPageFragment.LEAST_LINE
                }
                isExpandText = !isExpandText
            }

            imageViewProfileEdit.onThrottleClick {
                listener.onClickEdit()
            }
        }
    }

    fun bind(item: MyPageMyProfileVo) {
        binding.apply {
            if(item.profileImage.isNullOrEmpty()) imageViewProfile.setImageResource(R.drawable.iv_default_profile)
                else GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
            textViewProfileName.text = item.myName
            textViewProfileExplain.text = item.myIntro
            textViewReadMore.visibility = if(item.myIntro.length > MAX_LENGTH) View.VISIBLE else View.GONE
        }
    }
}