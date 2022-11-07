package com.plub.presentation.base

import android.os.Bundle
import android.widget.ProgressBar
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

    private lateinit var uiInspector:UiInspector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        uiInspector = UiInspector(this)

        initView()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                initState()
            }
        }
    }

    protected abstract fun initView()

    protected abstract suspend fun initState()

    protected fun bindProgressBar(progressBar: ProgressBar) {
        uiInspector.bindProgressView(progressBar)
    }

    protected fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: (T) -> Unit, individualFailCallback: ((T, IndividualFailure) -> Unit)? = null) {
        uiInspector.inspectUiState(uiState,succeedCallback, individualFailCallback)
    }
}
