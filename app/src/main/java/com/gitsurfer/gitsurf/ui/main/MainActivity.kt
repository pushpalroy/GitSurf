package com.gitsurfer.gitsurf.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.databinding.ActivityMainBinding
import com.gitsurfer.gitsurf.ui.base.AppNavigator
import com.gitsurfer.gitsurf.ui.base.BaseActivity
import com.gitsurfer.gitsurf.ui.login.LoginActivity
import com.gitsurfer.gitsurf.utils.exceptions.ForbiddenException
import com.gitsurfer.gitsurf.utils.exceptions.UnauthorizedException
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.navigationView
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.nav_header.view.iv_person_avatar
import kotlinx.android.synthetic.main.nav_header.view.tv_person_login
import kotlinx.android.synthetic.main.nav_header.view.tv_person_name

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

  override fun getViewModelClass() = MainViewModel::class.java
  override fun getLayoutId() = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setAuthorizedFromPref()
    setSupportActionBar(toolbar)
    setUpNavigation()
    listenToLiveData()
    viewModel.getLocalUserDetails()
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
      navigationView.getHeaderView(0).tv_person_name.text = roomUser.name
      navigationView.getHeaderView(0).tv_person_login.text = roomUser.login
      Glide.with(this)
          .load(roomUser.avatarUrl)
          .into(navigationView.getHeaderView(0).iv_person_avatar)
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
    return navigateUp(findNavController(R.id.nav_host_fragment), drawerLayout)
  }

  private fun setUpNavigation() {
    val navController = findNavController(R.id.nav_host_fragment)

    // Ensures that the title in the action bar will automatically be updated when the
    // destination changes. In addition, the Up button will be displayed when you are on a
    // non-root destination and the hamburger icon will be displayed when on the root destination
    setupActionBarWithNavController(navController, drawerLayout)

    // Handle nav drawer item clicks
    navigationView.setNavigationItemSelectedListener { menuItem ->
      menuItem.isChecked = true
      drawerLayout.closeDrawers()
      true
    }

    // Ensures that the selected item in the NavigationView will automatically be updated
    // when the destination changes
    setupWithNavController(navigationView, navController)
  }

  override fun onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }
}