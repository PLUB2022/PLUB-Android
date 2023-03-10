package com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.profileFold

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleProfileBinding
import com.plub.presentation.util.GlideUtil

class PlubingScheduleProfileFoldViewHolder(
    private val binding: LayoutRecyclerPlubingScheduleProfileBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val SHOW_MAX_PROFILE_COUNT: Int = 3
    }

    init {

    }

    fun bind(imageUrl: String, position: Int, allPeopleCount: Int) {
        val morePeopleCount = allPeopleCount - SHOW_MAX_PROFILE_COUNT
        binding.apply {
            if (position == SHOW_MAX_PROFILE_COUNT) {
                textViewMorePeopleNumber.text = root.context.getString(R.string.detail_recruitment_profile_county, morePeopleCount)
                imageViewProfile.visibility = View.GONE
            }
            else {
                GlideUtil.loadImage(root.context, imageUrl, imageViewProfile)
                imageViewProfile.clipToOutline = true
                imageViewProfile.visibility = View.VISIBLE
            }
        }
    }
}