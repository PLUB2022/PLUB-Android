package com.plub.presentation.ui.main.profile.setting

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.domain.model.enums.NoticeType
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentSettingBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import com.plub.presentation.util.IntentUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment :
    BaseTestFragment<FragmentSettingBinding, SettingState, SettingViewModel>(
        FragmentSettingBinding::inflate
    ) {

    companion object{
        const val TEXT_MAILTO = "mailto"
    }

    @Inject
    lateinit var commonDialog : CommonDialog

    override val viewModel: SettingViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as SettingEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: SettingEvent) {
        when(event){
            is SettingEvent.GoToBack -> {findNavController().popBackStack()}
            is SettingEvent.GoToEmail -> sendToEmail()
            is SettingEvent.GoToFAQ -> goToFAQ()
            is SettingEvent.GoToNotice -> goToNotice()
            is SettingEvent.GoToLogin -> goToSign()
            is SettingEvent.ShowLogoutDialog -> showLogoutDialog()
            is SettingEvent.ShowInactivationDialog -> showInactivationDialog()
            is SettingEvent.ShowRevokeDialog -> showRevokeDialog()
            is SettingEvent.GoToPersonalPolicesPage -> goToPersonalPolices()
            is SettingEvent.GoToServicePolicesPage -> goToServicePolices()
        }
    }

    private fun goToSign(){
        val intent = IntentUtil.getSignActivityIntent(requireContext())
        startActivity(intent)
        requireActivity().finish()
    }

    private fun sendToEmail(){
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            TEXT_MAILTO, context?.getString(R.string.plub_emil) ?: "", null)
        )
        startActivity(intent)
    }

    private fun goToFAQ(){
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.faq_site)))
        )
    }

    private fun goToNotice(){
        val action = SettingFragmentDirections.actionSettingToNotice(NoticeType.APP)
        findNavController().navigate(action)
    }

    private fun showLogoutDialog(){
        commonDialog
            .setTitle(R.string.logout_dialog)
            .setPositiveButton(R.string.my_page_setting_change_yes) {
                viewModel.onClickLogout()
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showInactivationDialog(){
        commonDialog
            .setTitle(R.string.inactivation_dialog)
            .setDescription(R.string.inactivation_dialog_description)
            .setPositiveButton(R.string.my_page_setting_change_yes) {
                viewModel.onClickInactivation()
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showRevokeDialog(){
        commonDialog
            .setTitle(R.string.revoke_dialog)
            .setDescription("")
            .setPositiveButton(R.string.my_page_setting_change_yes) {
                viewModel.onClickRevoke()
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                commonDialog.dismiss()
            }
            .show()
    }

    private fun goToServicePolices(){
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.service_policies)))
        )
    }

    private fun goToPersonalPolices(){
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.personal_policies)))
        )
    }
}