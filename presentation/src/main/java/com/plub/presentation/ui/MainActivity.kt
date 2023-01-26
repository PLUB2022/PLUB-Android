package com.plub.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.plub.domain.model.enums.BottomNavigationItemType
import com.plub.presentation.R
import com.plub.presentation.base.BaseActivity
import com.plub.presentation.databinding.ActivityMainBinding
import com.plub.presentation.event.MainEvent
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.home.plubing.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, PageState.Default, MainViewModel>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private val bottomNavigationFragmentIds = listOf(
        R.id.menu_navigation_main,
        R.id.menu_navigation_gathering,
        R.id.menu_navigation_noti,
        R.id.menu_navigation_profile
    )

    override fun initView() {
        binding.apply {
            vm = viewModel
            initNavigation()
        }
    }

    override fun initState() {
        super.initState()

        repeatOnStarted {
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as MainEvent)
                }
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fc_main_host) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        BottomNavigationItemType.values().indices.forEach {
            addBottomNavigationViewBadge(it)
        }

        showBadge(BottomNavigationItemType.MAIN.idx)

        binding.bottomNavigationView.setOnItemReselectedListener { }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in bottomNavigationFragmentIds) {
                viewModel.onSelectedBottomNavigationMenu(destination.id)
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

    private fun inspectEventFlow(event: MainEvent) {
        when (event) {
            is MainEvent.ShowBottomNavigationBadge -> {
                showBadge(event.index)
            }
        }
    }

    private fun showBadge(index: Int) {
        BottomNavigationItemType.values().indices.forEach {
            if (index == it) showBottomNavigationViewBadge(it) else hideBottomNavigationViewBadge(it)
        }
    }

    private fun addBottomNavigationViewBadge(index: Int) {
        val menuView = binding.bottomNavigationView.getChildAt(0) as? BottomNavigationMenuView
        val itemView = menuView?.getChildAt(index) as? BottomNavigationItemView
        val badge: View =
            LayoutInflater.from(this).inflate(R.layout.include_navigation_badge, menuView, false)
        itemView?.addView(badge)
    }

    private fun showBottomNavigationViewBadge(index: Int) {
        val menuView = binding.bottomNavigationView.getChildAt(0) as? BottomNavigationMenuView
        val itemView = menuView?.getChildAt(index) as? BottomNavigationItemView
        itemView?.apply {
            findViewById<ImageView>(R.id.image_view_badge).visibility = View.VISIBLE
        }
    }

    private fun hideBottomNavigationViewBadge(index: Int) {
        val menuView = binding.bottomNavigationView.getChildAt(0) as? BottomNavigationMenuView
        val itemView = menuView?.getChildAt(index) as? BottomNavigationItemView
        itemView?.apply {
            findViewById<ImageView>(R.id.image_view_badge).visibility = View.GONE
        }
    }

}
