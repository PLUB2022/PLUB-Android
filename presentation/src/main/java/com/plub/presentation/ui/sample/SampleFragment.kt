package com.plub.presentation.ui.sample

import androidx.fragment.app.viewModels
import com.plub.domain.model.SampleLogin
import com.plub.domain.model.state.SampleLoginPageState
import com.plub.domain.result.LoginFailure
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSampleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleFragment : BaseFragment<FragmentSampleBinding,SampleLoginPageState,SampleFragmentViewModel>(
    R.layout.fragment_sample
) {

    override val viewModel: SampleFragmentViewModel by viewModels()

    override suspend fun initState() {
        viewModel.uiState.collect {
            inspectUiState(it.loginData, ::handleSampleLogin) { sampleLogin, individualFailure ->
                //TODO 해당 부분 처리하는 것도 base에 Inspect쪽 클래스 만들며 같이 뺄 수 있으면 빼는게 좋아보임
                handleLoginFail(sampleLogin, individualFailure as LoginFailure)
            }
        }
    }

    override fun initView() {
        binding.viewModel = viewModel
    }

    private fun handleSampleLogin(sampleLogin: SampleLogin) {
        //TODO handleSampleLogin
    }

    private fun handleLoginFail(sampleLogin: SampleLogin,loginFailure: LoginFailure) {
        //TODO handleLoginFail
        when(loginFailure) {
            is LoginFailure.InvalidedAccount -> Unit
        }
    }
}