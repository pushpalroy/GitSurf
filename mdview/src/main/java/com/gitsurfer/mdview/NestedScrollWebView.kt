package com.gitsurfer.mdview

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import androidx.core.view.MotionEventCompat
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

open class NestedScrollWebView :
  WebView,
  NestedScrollingChild {

  private var lastY = 0
  private val scrollOffset = IntArray(2)
  private val mScrollConsumed = IntArray(2)
  private var nestedOffsetY = 0
  private var firstScroll = true
  private var interceptTouch = false
  private var childHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)

  constructor(
    context: Context
  ) : this(context, null)

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : this(context, attrs, attr.webViewStyle)

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr) {
    isNestedScrollingEnabled = true
  }

  override fun dispatchNestedScroll(
    dxConsumed: Int,
    dyConsumed: Int,
    dxUnconsumed: Int,
    dyUnconsumed: Int,
    offsetInWindow: IntArray?
  ): Boolean {
    return childHelper
      .dispatchNestedScroll(
        dxConsumed,
        dyConsumed,
        dxUnconsumed,
        dyUnconsumed,
        offsetInWindow
      )
  }

  override fun isNestedScrollingEnabled(): Boolean {
    return childHelper.isNestedScrollingEnabled
  }

  override fun dispatchNestedPreScroll(
    dx: Int,
    dy: Int,
    consumed: IntArray?,
    offsetInWindow: IntArray?
  ): Boolean {
    return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
  }

  override fun stopNestedScroll() {
    childHelper.stopNestedScroll()
  }

  override fun hasNestedScrollingParent(): Boolean {
    return childHelper.hasNestedScrollingParent()
  }

  override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
    return childHelper.dispatchNestedPreFling(velocityX, velocityY)
  }

  override fun setNestedScrollingEnabled(enabled: Boolean) {
    childHelper.isNestedScrollingEnabled = enabled
  }

  override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
    return childHelper.dispatchNestedFling(velocityX, velocityY, consumed)
  }

  override fun startNestedScroll(axes: Int): Boolean {
    return childHelper.startNestedScroll(axes)
  }

  fun setInterceptTouch(interceptTouch: Boolean) {
    this.interceptTouch = interceptTouch
  }

  override fun onTouchEvent(ev: MotionEvent?): Boolean {
    if (parent != null) {
      parent.requestDisallowInterceptTouchEvent(interceptTouch)
    }
    val returnValue: Boolean
    val event = MotionEvent.obtain(ev)
    val action = MotionEventCompat.getActionMasked(event)
    if (action == MotionEvent.ACTION_DOWN) {
      nestedOffsetY = 0
    }
    val eventY = event.y.toInt()
    event.offsetLocation(0f, nestedOffsetY.toFloat())
    when (action) {
      MotionEvent.ACTION_MOVE -> {
        var deltaY = lastY - eventY
        if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, scrollOffset)) {
          deltaY -= mScrollConsumed[1]
          lastY = eventY - scrollOffset[1]
          event.offsetLocation(0f, (-scrollOffset[1]).toFloat())
          nestedOffsetY += scrollOffset[1]
        }
        returnValue = super.onTouchEvent(event)

        if (dispatchNestedScroll(0, scrollOffset[1], 0, deltaY, scrollOffset)) {
          event.offsetLocation(0f, scrollOffset[1].toFloat())
          nestedOffsetY += scrollOffset[1]
          lastY -= scrollOffset[1]
        }
      }
      MotionEvent.ACTION_DOWN -> {
        returnValue = super.onTouchEvent(event)
        if (firstScroll) {
          lastY = eventY - 5
          firstScroll = false
        } else {
          lastY = eventY
        }
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
      }
      else -> {
        returnValue = super.onTouchEvent(event)
        stopNestedScroll()
      }
    }
    return returnValue
  }
}