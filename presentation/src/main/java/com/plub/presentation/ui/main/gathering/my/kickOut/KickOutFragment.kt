package com.plub.presentation.ui.main.gathering.my.kickOut

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentKickOutBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import com.plub.presentation.ui.main.gathering.my.adapter.MyGatheringsAdapter
import com.plub.presentation.ui.main.gathering.my.kickOut.adapter.AccountInfoAdapter
import com.plub.presentation.ui.main.home.categoryGathering.CategoryGatheringFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class KickOutFragment : BaseTestFragment<FragmentKickOutBinding, KickOutPageState, KickOutViewModel>(
    FragmentKickOutBinding::inflate
) {
    @Inject
    lateinit var commonDialog: CommonDialog

    private val navArgs: KickOutFragmentArgs by navArgs()
    override val viewModel: KickOutViewModel by viewModels()

    private val accountInfoAdapter: AccountInfoAdapter by lazy {
        AccountInfoAdapter {
            showKickOutDialog(it)
        }
    }

    private fun showKickOutDialog(accountInfo: AccountInfoVo) {
        commonDialog
            .setTitle(getString(R.string.modal_kick_out_user, accountInfo.nickname))
            .setPositiveButton(R.string.modal_yes_i_do) {
                viewModel.kickOutMember(navArgs.plubingId, accountInfo.userId)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.modal_cancel) {
                commonDialog.dismiss()
            }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMembers(navArgs.plubingId)
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerViewMembers.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = accountInfoAdapter
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.members.collect {
                    accountInfoAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    if(it is KickOutEvent.GoToBack) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}