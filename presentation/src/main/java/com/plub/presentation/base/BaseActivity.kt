package com.plub.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plub.domain.UiState
import com.plub.domain.model.state.PageState
import com.plub.domain.result.CommonFailure
import com.plub.domain.result.IndividualFailure
import com.plub.domain.result.StateResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<B : ViewDataBinding,STATE: PageState, VM: BaseViewModel<STATE>>(
    private val inflater: (LayoutInflater) -> B,
) : AppCompatActivity() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    private lateinit var uiInspector:UiInspector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflater(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        uiInspector = UiInspector(this)

        initView()
        initState()
    }

    protected abstract fun initView()

    protected abstract fun initState()

    protected fun bindProgressBar(progressBar: ProgressBar) {
        uiInspector.bindProgressView(progressBar)
    }

    protected fun<T> inspectUiState(uiState: UiState<T>, succeedCallback: (T) -> Unit, individualFailCallback: ((T, IndividualFailure) -> Unit)? = null) {
        uiInspector.inspectUiState(uiState,succeedCallback, individualFailCallback)
    }

    protected fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}
