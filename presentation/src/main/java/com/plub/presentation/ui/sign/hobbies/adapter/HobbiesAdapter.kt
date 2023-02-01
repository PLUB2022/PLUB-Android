package com.plub.presentation.ui.sign.hobbies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.HobbyViewType
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.databinding.IncludeInterestRegisterTitleBinding
import com.plub.presentation.databinding.IncludeItemHobbyBinding
import com.plub.presentation.databinding.IncludeItemHobbyLatePickBinding
import com.plub.presentation.ui.main.home.registinterests.InterestRegisterFirstViewHolder

class HobbiesAdapter(
    private val listener: Delegate
) : ListAdapter<HobbyVo, RecyclerView.ViewHolder>(HobbyDiffCallback()) {

    private val subListenerList: MutableSet<SubListener> = mutableSetOf()

    interface Delegate {
        val selectedList:List<SelectedHobbyVo>
        fun onClickExpand(hobbyId: Int)
        fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo)
        fun onClickLatePick()
    }

    interface SubListener {
        fun onNotifySubItemChange(parentId: Int, subId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HobbyViewHolder -> holder.bind(currentList[position])
            is HobbyLatePickViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (HobbyViewType.valueOf(viewType)) {
            HobbyViewType.HOBBY -> {
                val binding = IncludeItemHobbyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HobbyViewHolder(binding, listener).apply {
                    subListenerList.add(this)
                }
            }
            HobbyViewType.LATE_PICK -> {
                val binding = IncludeItemHobbyLatePickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HobbyLatePickViewHolder(binding, listener)
            }

            HobbyViewType.FIRST_TOPIC -> {
                val binding = IncludeInterestRegisterTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InterestRegisterFirstViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }

    fun notifySubItemUpdate(selectedVo: SelectedHobbyVo) {
        subListenerList.forEach {
            it.onNotifySubItemChange(selectedVo.parentId, selectedVo.subId)
        }
    }

    fun notifyAllItemUpdate() {
        notifyDataSetChanged()
    }
}

class HobbyDiffCallback : DiffUtil.ItemCallback<HobbyVo>() {
    override fun areItemsTheSame(oldItem: HobbyVo, newItem: HobbyVo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HobbyVo, newItem: HobbyVo): Boolean =
        oldItem.isExpand == newItem.isExpand &&
                oldItem.name == newItem.name &&
                oldItem.subHobbies == newItem.subHobbies &&
                oldItem.icon == newItem.icon
}