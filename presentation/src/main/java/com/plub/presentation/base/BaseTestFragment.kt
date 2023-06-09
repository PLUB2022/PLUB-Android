package com.plub.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.common.dialog.ProgressDialog
import com.plub.presentation.util.CommonProcessor
import com.plub.presentation.util.PlubLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseTestFragment<B : ViewDataBinding, STATE: PageState, VM: BaseTestViewModel<STATE>>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
) : Fragment() {

    protected abstract val viewModel: VM

    private var _binding: B? = null
    protected val binding
        get() = _binding!!

    private lateinit var commonProcessor: CommonProcessor
    private var progressView: ProgressDialog? = null

    private var loadingTaskCount: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        progressView = ProgressDialog(requireContext())
    }

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
        initStates()
    }

    protected abstract fun initView()

    protected open fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.showProgress.collect {
                    if(it) loadingTaskCount += 1
                    else if(loadingTaskCount > 0) loadingTaskCount -= 1

                    if(loadingTaskCount > 0) progressView?.show()
                    else progressView?.dismiss()
                }
            }

            launch {
                viewModel.commonError.collect {
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
        progressView?.dismiss()
        progressView = null
        super.onDestroyView()
    }
}
