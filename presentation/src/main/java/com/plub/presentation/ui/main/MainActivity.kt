package com.plub.presentation.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.plub.domain.model.enums.BottomNavigationItemType
import com.plub.presentation.R
import com.plub.presentation.base.BaseActivity
import com.plub.presentation.databinding.ActivityMainBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.util.IntentUtil.NAVIGATION_BUNDLE
import com.plub.presentation.util.NavigationBundle
import com.plub.presentation.util.ResourceProvider
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, PageState.Default, MainViewModel>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val destinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        viewModel.onSelectedBottomNavigationMenu(destination.id)
        viewModel.onDestinationChanged(destination.id)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        viewModel.emitProcessIntent(intent)
    }

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

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onPause() {
        super.onPause()

        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main_host) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        BottomNavigationItemType.values().indices.forEach {
            addBottomNavigationViewBadge(it)
        }

        showBadge(BottomNavigationItemType.MAIN.idx)
    }

    private fun inspectEventFlow(event: MainEvent) {
        when (event) {
            is MainEvent.ShowBottomNavigationBadge -> {
                showBadge(event.index)
            }
            is MainEvent.BottomNavigationVisibility -> {
                bottomNavigationVisibility(event.isVisible)
            }
            is MainEvent.ChangeStatusBarColor -> {
                changeStatusBarColor(event.colorId)
            }
            is MainEvent.Navigate -> navControllerNavigate(event.navigationBundle)
            MainEvent.PopBackStack -> navControllerPopBackStack()
            is MainEvent.ProcessIntent -> processIntent(event.intent)
        }
    }

    private fun processIntent(intent: Intent?) {
        val navigationBundle = intent?.parcelable<NavigationBundle>(NAVIGATION_BUNDLE) ?: return

        viewModel.emitPopBackStackEventIfCurrentIdSameDestination(navController.currentDestination?.id, navigationBundle.destination)
        viewModel.emitNavigate(navigationBundle)
    }

    private fun navControllerPopBackStack() {
        navController.popBackStack()
    }

    private fun navControllerNavigate(navigationBundle: NavigationBundle) {
        navController.navigate(
            navigationBundle.destination,
            navigationBundle.bundle
        )
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


    private fun bottomNavigationVisibility(isVisible:Boolean) {
        binding.bottomNavigationView.visibility = if(isVisible) View.VISIBLE else View.GONE
    }

    private fun changeStatusBarColor(colorId: Int) {
        window.statusBarColor = resourceProvider.getColor(colorId)
    }
}
