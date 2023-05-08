package com.plub.presentation.ui.main.noti

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentNotiBinding
import com.plub.presentation.ui.main.MainViewModel
import com.plub.presentation.ui.main.noti.adapter.PlubingNotificationAdapter
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.NotificationUtil
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotiFragment : BaseTestFragment<FragmentNotiBinding, NotiPageState, NotiViewModel>(
    FragmentNotiBinding::inflate
) {
    override val viewModel: NotiViewModel by viewModels()
    private val parentViewModel by activityViewModels<MainViewModel>()

    private val listAdapter: PlubingNotificationAdapter by lazy {
        PlubingNotificationAdapter {
            viewModel.readNotification(it.notificationId)
            val intent = IntentUtil.getMainActivityIntent(requireContext())
            intent.putExtra(IntentUtil.NAVIGATION_BUNDLE, NotificationUtil.getBundleAndDestination(it.notificationType, it.redirectTargetId))
            parentViewModel.emitProcessIntent(intent)
        }
    }

    override fun initView() {
        viewModel.initNoti()

        binding.apply {
            vm = viewModel
            recyclerViewNotification.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            viewModel.uiState.notiList.onEach {
                listAdapter.submitList(it)
            }.launchIn(this)
        }
    }
}