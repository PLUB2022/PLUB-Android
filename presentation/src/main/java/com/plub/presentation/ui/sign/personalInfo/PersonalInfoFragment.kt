package com.plub.presentation.ui.sign.personalInfo

import android.widget.ListPopupWindow
import android.widget.Spinner
import androidx.fragment.app.viewModels
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.PersonalInfoPageState
import com.plub.domain.model.vo.signUp.SignUpListener
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPersonalInfoBinding
import com.plub.presentation.ui.sign.signup.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.reflect.Field

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding, PersonalInfoPageState, PersonalInfoViewModel>(
    FragmentPersonalInfoBinding::inflate
),SignUpListener {

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

    override fun initState() {
        super.initState()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.moveToNextPage.collect {
                    delegate?.onMoveToNextPage(SignUpPageType.PERSONAL_INFO, it)
                }
            }
        }

    }

    override fun initPage(signUpPageVo: SignUpPageVo?) {
    }
}