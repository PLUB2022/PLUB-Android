package com.plub.presentation.ui.sign.personalInfo

import androidx.fragment.app.viewModels
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.PersonalInfoPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPersonalInfoBinding
import com.plub.presentation.ui.sign.signup.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding, PersonalInfoPageState, PersonalInfoViewModel>(
    FragmentPersonalInfoBinding::inflate
) {

    companion object {
        fun newInstance(delegate: SignUpFragment.Delegate) = PersonalInfoFragment().apply {
            this.delegate = delegate
        }
    }

    override val viewModel: PersonalInfoViewModel by viewModels()

    private var delegate:SignUpFragment.Delegate? = null

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.moveToNextPage.collect {
                    delegate?.onMoveToNextPage(SignUpPageType.PERSONAL_INFO, it)
                }
            }
        }

        viewModel.initSpinner()
    }
}