package com.plub.presentation.ui.main.home.profile.setting

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    }

    private fun goToNotice(){

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
}