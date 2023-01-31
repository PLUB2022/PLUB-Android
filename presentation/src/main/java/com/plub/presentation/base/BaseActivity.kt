package com.plub.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.CommonProcessor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<B : ViewDataBinding,STATE: PageState, VM: BaseViewModel<STATE>>(
    private val inflater: (LayoutInflater) -> B,
) : AppCompatActivity() {

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    private lateinit var commonProcessor: CommonProcessor
    private var progressView:ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflater(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        commonProcessor = CommonProcessor(this)

        initView()
        initState()
    }

    protected abstract fun initView()


    protected open fun initState() {
        repeatOnStarted {
            launch {
                viewModel.showProgress.collect {
                    progressView?.visibility = if(it) View.VISIBLE else View.GONE
                }
            }

            launch {
                viewModel.commonError.collect {
                    commonProcessor.errorProcess(it)
                }
            }
        }
    }

    protected fun bindProgressBar(progressBar: ProgressBar) {
        progressView = progressBar
    }

    protected fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}
