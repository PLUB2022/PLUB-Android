package com.plub.presentation.ui.sign.signup.authentication

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentGetPhoneBinding
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import com.plub.presentation.ui.sign.signup.moreInfo.MoreInfoPageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetPhoneFragment : BaseTestFragment<FragmentGetPhoneBinding, GetPhonePageState, GetPhoneViewModel>(
    FragmentGetPhoneBinding::inflate
) {

    override val viewModel: GetPhoneViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {

            }

            launch {

            }
        }
    }

//    private fun inspectEventFlow(event: MoreInfoEvent) {
//        when(event) {
//            is MoreInfoEvent.MoveToNext -> {
//                parentViewModel.onMoveToNextPage(SignUpPageType.MORE_INFO, event.vo)
//            }
//        }
//    }
}