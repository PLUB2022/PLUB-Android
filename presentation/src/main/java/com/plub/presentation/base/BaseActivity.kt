package com.plub.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plub.domain.UiState
import com.plub.domain.model.state.PageState
import com.plub.domain.result.CommonFailure
import com.plub.domain.result.IndividualFailure
import com.plub.domain.result.StateResult
import kotlinx.coroutines.launch

abstract class BaseActivity<B : ViewDataBinding,STATE: PageState, VM: BaseViewModel<STATE>>(
    private val layoutRes:Int
) : AppCompatActivity() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
        setContentView(binding.root)

        initView()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                initState()
            }
        }
    }

    protected abstract fun initView()

    protected abstract suspend fun initState()

    /**
     * TODO : 해당 Inspect 함수들이 Base에 존재하기에 너무 사이즈가 크다고 생각, Fragment와 Activity 코드가 중복됨
     * TODO : UiInspect 클래스를 만들거나 하여 코드 간소화 필요
     */

    protected fun<T> inspectUiState(uiState: UiState<T>,succeedCallback: (T) -> Unit, individualFailCallback: (T, IndividualFailure) -> Unit) {
        when(uiState) {
            is UiState.Loading -> showLoading()
            is UiState.Error -> handleError()
            is UiState.Success -> handleSuccess(uiState,succeedCallback, individualFailCallback)
        }
    }

    private fun showLoading() {
        //TODO show loading
    }

    private fun handleError() {
        //TODO handleError
    }

    private fun<T> handleSuccess(success: UiState.Success<T>,succeedCallback: (T) -> Unit, individualFailCallback: (T, IndividualFailure) -> Unit) {
        when(val result = success.result) {
            is CommonFailure -> handleCommonFailure(result)
            is StateResult.Succeed -> succeedCallback.invoke(success.data)
            is IndividualFailure -> individualFailCallback.invoke(success.data, result)
        }
    }

    private fun handleCommonFailure(failure: CommonFailure) {
        //TODO handleCommonFailure
//        when(failure) {
//            is TokenInvalided -> {}
//        }
    }
}
