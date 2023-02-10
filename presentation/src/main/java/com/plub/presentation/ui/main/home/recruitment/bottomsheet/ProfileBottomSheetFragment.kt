package com.plub.presentation.ui.main.home.recruitment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.databinding.BottomSheetRecruitSeeMoreProfileBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.util.px

class ProfileBottomSheetFragment(private val profileList : List<RecruitDetailJoinedAccountsListVo>) : BottomSheetDialogFragment() {
    private val binding: BottomSheetRecruitSeeMoreProfileBinding by lazy {
        BottomSheetRecruitSeeMoreProfileBinding.inflate(layoutInflater)
    }

    companion object{
        const val ITEM_SPAN_COUNT = 4
    }

    private val bottomSheetProfileAdapter: BottomSheetProfileAdapter by lazy {
        BottomSheetProfileAdapter(object : BottomSheetProfileAdapter.ProfileDelegate {
            override fun onProfileClick(accountId: Int) {
                goToProfile(accountId)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetProfileAdapter.submitList(profileList)

        binding.recyclerViewAllProfile.apply {
            addItemDecoration(GridSpaceDecoration(ITEM_SPAN_COUNT, 6.px, 8.px,false))
            layoutManager = GridLayoutManager(context, ITEM_SPAN_COUNT)
            adapter = bottomSheetProfileAdapter
        }
    }

    private fun goToProfile(accountId : Int){
        dismiss()
    }
}