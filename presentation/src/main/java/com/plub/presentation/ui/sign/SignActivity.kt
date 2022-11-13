package com.plub.presentation.ui.sign

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.plub.domain.model.state.PageState
import com.plub.presentation.R
import com.plub.presentation.base.BaseActivity
import com.plub.presentation.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class SignActivity : BaseActivity<ActivitySignBinding,PageState.Default,SignViewModel>(ActivitySignBinding::inflate) {

    override val viewModel: SignViewModel by viewModels()
    private lateinit var navController: NavController

    override fun initView() {
        binding.apply {
            vm = viewModel
            initNavigation()
        }
    }

    override fun initState() {

    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
    }

}