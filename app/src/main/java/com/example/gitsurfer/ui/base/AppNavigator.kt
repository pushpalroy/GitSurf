package com.example.gitsurfer.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object AppNavigator {

  fun startActivity(
    activityClass: Class<out Activity>,
    activity: Activity,
    finishAfter: Boolean = false,
    withoutAnimation: Boolean = false,
    clearBackStack: Boolean = false
  ) {
    val intent = Intent(activity, activityClass)
    if (clearBackStack) {
      intent.addFlags(
          Intent.FLAG_ACTIVITY_CLEAR_TOP or
              Intent.FLAG_ACTIVITY_CLEAR_TASK or
              Intent.FLAG_ACTIVITY_NEW_TASK
      )
    }
    activity.startActivity(intent)
    if (finishAfter) {
      finishActivityWithAnimation(activity = activity)
    }
    if (!withoutAnimation) {
      activity.overridePendingTransition(0, 0)
    }
  }

  fun startActivityWithData(
    activityClass: Class<out AppCompatActivity>,
    bundle: Bundle,
    activity: AppCompatActivity
  ) {
    val intent = Intent(activity, activityClass)
    intent.putExtras(bundle)
    activity.startActivity(intent)
  }

  fun startActivityWithAnimation(
    activityClass: Class<out AppCompatActivity>,
    inAnimation: Int,
    outAnimation: Int,
    activity: AppCompatActivity
  ) {
    val intent = Intent(activity, activityClass)
    activity.startActivity(intent)
    activity.overridePendingTransition(inAnimation, outAnimation)
  }

  fun startActivityWithDataAndAnimation(
    activityClass: Class<out AppCompatActivity>,
    bundle: Bundle,
    inAnimation: Int,
    outAnimation: Int,
    activity: AppCompatActivity
  ) {
    val intent = Intent(activity, activityClass)
    intent.putExtras(bundle)
    activity.startActivity(intent)
    activity.overridePendingTransition(inAnimation, outAnimation)
  }

  fun addFragment(
    containerId: Int,
    fragment: Fragment,
    activity: AppCompatActivity,
    addToBackStack: Boolean = false
  ) {
    var transaction = activity.supportFragmentManager.beginTransaction()
        .add(containerId, fragment, fragment.javaClass.name)
    if (addToBackStack) {
      transaction = transaction.addToBackStack(fragment.javaClass.name)
    }
    transaction.commit()
  }

  fun replaceFragment(
    containerId: Int,
    fragment: Fragment,
    activity: AppCompatActivity,
    addToBackStack: Boolean = false
  ) {
    var transaction = activity.supportFragmentManager.beginTransaction()
        .replace(containerId, fragment, fragment.javaClass.name)
    if (addToBackStack) {
      transaction = transaction.addToBackStack(fragment.javaClass.name)
    }
    transaction.commit()
  }

  fun finishActivityWithAnimation(
    inAnimation: Int? = null,
    outAnimation: Int? = null,
    activity: Activity
  ) {
    activity.finish()
    inAnimation?.let {
      outAnimation?.let { it1 ->
        activity.overridePendingTransition(
            it,
            it1
        )
      }
    }
  }

  fun startActivityWithDataForResult(
    activityClass: Class<out AppCompatActivity>,
    bundle: Bundle,
    activity: AppCompatActivity,
    requestCode: Int
  ) {
    val intent = Intent(activity, activityClass)
    intent.putExtras(bundle)
    activity.startActivityForResult(intent, requestCode)
  }

  fun startActivityWithDataForResult(
    activityClass: Class<out AppCompatActivity>,
    bundle: Bundle,
    fragment: Fragment,
    requestCode: Int
  ) {
    val intent = Intent(fragment.context, activityClass)
    intent.putExtras(bundle)
    fragment.startActivityForResult(intent, requestCode)
  }

  fun startActivityWithData(
    activityClass: Class<out AppCompatActivity>,
    bundle: Bundle,
    fragment: Fragment
  ) {
    val intent = Intent(fragment.context, activityClass)
    intent.putExtras(bundle)
    fragment.startActivity(intent)
  }
}