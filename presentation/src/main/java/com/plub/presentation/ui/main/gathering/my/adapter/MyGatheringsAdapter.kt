package com.plub.presentation.ui.main.gathering.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyGatheringsViewType
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringContentBinding
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringCreateBinding
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringParticipateBinding
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.PlubingScheduleContentViewHolder
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.PlubingScheduleLoadingViewHolder
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.PlubingScheduleYearViewHolder
import com.plub.presentation.util.onThrottleClick

class MyGatheringsAdapter(
    private val onMyGatheringMeatBallClick: () -> Unit = { },
    private val onMyHostingMeatBallClick: () -> Unit = { },
    private val onCreateGatheringClick: () -> Unit = { },
    private val onParticipateGatheringClick: () -> Unit = { },
) : ListAdapter<MyGatheringResponseVo, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MyGatheringResponseVo>() {
            override fun areItemsTheSame(
                oldItem: MyGatheringResponseVo,
                newItem: MyGatheringResponseVo
            ): Boolean {
                return oldItem.plubbingId == newItem.plubbingId && oldItem.viewType == newItem.viewType
            }

            override fun areContentsTheSame(
                oldItem: MyGatheringResponseVo,
                newItem: MyGatheringResponseVo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyGatheringsViewHolder -> { holder.bind(currentList[position]) }
            is ParticipateGatheringViewHolder -> {}
            is CreateGatheringViewHolder -> {}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MyGatheringsViewType.valueOf(viewType)) {
            MyGatheringsViewType.MY_GATHERING_CONTENT -> {
                val binding = LayoutRecyclerMyGatheringContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MyGatheringsViewHolder(binding, onMyGatheringMeatBallClick = onMyGatheringMeatBallClick)
            }

            MyGatheringsViewType.MY_HOSTING_CONTENT -> {
                val binding = LayoutRecyclerMyGatheringContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MyGatheringsViewHolder(binding, onMyHostingMeatBallClick = onMyHostingMeatBallClick)
            }

            MyGatheringsViewType.PARTICIPATE -> {
                val binding = LayoutRecyclerMyGatheringParticipateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ParticipateGatheringViewHolder(binding, onParticipateGatheringClick)
            }

            MyGatheringsViewType.CREATE -> {
                val binding = LayoutRecyclerMyGatheringCreateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CreateGatheringViewHolder(binding, onCreateGatheringClick)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}