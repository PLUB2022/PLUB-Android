package com.plub.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plub.domain.UiState
import com.plub.domain.model.state.PageState
import com.plub.domain.result.IndividualFailure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<B : ViewDataBinding, STATE: PageState, VM: BaseViewModel<STATE>>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
) : Fragment() {

    protected abstract val viewModel: VM

    private var _binding: B? = null
    protected val binding
        get() = _binding!!

    private lateinit var commonProcessor: CommonProcessor
    private var progressView:ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflater(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        commonProcessor = CommonProcessor(requireContext())

        initView()
        initState()
    }

    protected fun bindProgressBar(progressBar: ProgressBar) {
        progressView = progressBar
    }

    protected abstract fun initView()

    protected open fun initState() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.showProgress.collect {
                    progressView?.visibility = if(it) View.VISIBLE else View.GONE
                }
            }

            launch {
                viewModel.commonFailure.collect {
                    commonProcessor.failProcess(it)
                }
            }

            launch {
                viewModel.uiError.collect {
                    commonProcessor.errorProcess(it)
                }
            }
        }
    }

    protected fun LifecycleOwner.repeatOnStarted(viewLifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
