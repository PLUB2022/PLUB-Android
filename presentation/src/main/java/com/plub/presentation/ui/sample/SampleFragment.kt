package com.plub.presentation.ui.sample

import androidx.fragment.app.viewModels
import com.plub.domain.model.vo.login.SampleLogin
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
                handleLoginFail(sampleLogin, individualFailure as LoginFailure) //개별 에러 처리까지 할 때
            }

            inspectUiState(it.loginData, ::handleSampleLogin) //공통 에러 처리만 할 때
            inspectUiState(it.loginData) //데이터 바인딩을 하여 오직 로딩만 적용할 때
        }
    }

    override fun initView() {
        bindProgressBar(binding.loadingBar)
        binding.viewModel = viewModel
    }

    private fun handleSampleLogin(sampleLogin: SampleLogin) {

    }

    private fun handleLoginFail(sampleLogin: SampleLogin, loginFailure: LoginFailure) {
        when(loginFailure) {
            is LoginFailure.InvalidedAccount -> Unit
            LoginFailure.Common -> Unit
        }
    }
}