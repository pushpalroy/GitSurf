package com.gitsurfer.gitsurf.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.R.string
import com.gitsurfer.gitsurf.databinding.ActivityMainBinding
import com.gitsurfer.gitsurf.databinding.NavHeaderBinding
import com.gitsurfer.gitsurf.ui.base.BaseActivity
import com.gitsurfer.gitsurf.ui.login.LoginActivity
import com.gitsurfer.gitsurf.utils.exceptions.ForbiddenException
import com.gitsurfer.gitsurf.utils.exceptions.UnauthorizedException
import com.gitsurfer.gitsurf.utils.ui.AppNavigator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.navigationView
import kotlinx.android.synthetic.main.activity_main.toolbar

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

  override val viewModel: MainViewModel by viewModels()
  override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

  private val navController: NavController by lazy {
    findNavController(
      R.id.nav_host_fragment
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    viewModel.setAuthorizedFromPref()
    setSupportActionBar(toolbar)
    setUpNavigation()
    listenToLiveData()
    viewModel.getLocalUserDetails()
    viewModel.registerNetworkListener()
  }

  private fun listenToLiveData() {
    viewModel.isAuthorizedLiveData.observe(this, Observer { isAuthorized ->
      if (!isAuthorized) {
        navigateToLoginActivity()
      }
    })

    viewModel.exceptionLiveData.observe(this, Observer { exception ->
      if (exception is UnauthorizedException || exception is ForbiddenException) {
        viewModel.setAuthorized(isAuthorized = false)
      }
    })

    viewModel.progressLiveData.observe(this, Observer { isLoading ->
      when (isLoading) {
        true -> binding.pbInitialLoader.visibility = View.VISIBLE
        false -> binding.pbInitialLoader.visibility = View.GONE
      }
    })

    viewModel.roomUserLiveData.observe(this, Observer { roomUser ->
      NavHeaderBinding.bind(navigationView.getHeaderView(0)).roomUser = roomUser
    })

    viewModel.isInternetAvailable.observe(this, Observer { isAvailable ->
      if (!isAvailable) {
        showSnackBar(string.lost_internet_message, binding.root, Snackbar.LENGTH_INDEFINITE)
      } else {
        dismissSnackBar()
      }
    })
  }

  private fun navigateToLoginActivity() {
    AppNavigator.startActivity(
      activityClass = LoginActivity::class.java,
      activity = this,
      clearBackStack = true
    )
  }

  override fun onSupportNavigateUp(): Boolean {
    // Ensures that the menu items in the Nav Drawer stay in sync with the navigation graph
    return navigateUp(navController, drawerLayout)
  }

  private fun setUpNavigation() {

    // Ensures that the title in the action bar will automatically be updated when the
    // destination changes. In addition, the Up button will be displayed when you are on a
    // non-root destination and the hamburger icon will be displayed when on the root destination
    setupActionBarWithNavController(navController, drawerLayout)

    // Ensures that the selected item in the NavigationView will automatically be updated
    // when the destination changes
    setupWithNavController(navigationView, navController)

    // Handle nav drawer item clicks
    navigationView.setNavigationItemSelectedListener { menuItem ->
      drawerLayout.closeDrawers()

      if (!menuItem.isChecked) {
        when (menuItem.itemId) {
          R.id.nav_feed -> navController.navigate(R.id.feedFragment)
          R.id.nav_bookmark -> navController.navigate(R.id.bookmarksFragment)
        }
        menuItem.isChecked = true
      }

      true
    }

    navigationView.menu[0].isChecked = true
  }

  override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }
}