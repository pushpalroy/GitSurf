package com.gitsurfer.gitsurf.utils.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gitsurfer.gitsurf.R
import com.gitsurfer.gitsurf.utils.ui.ButtonState.GONE
import com.gitsurfer.gitsurf.utils.ui.ButtonState.LEFT_VISIBLE
import com.gitsurfer.gitsurf.utils.ui.ButtonState.RIGHT_VISIBLE

internal enum class ButtonState {
  GONE,
  LEFT_VISIBLE,
  RIGHT_VISIBLE
}

class SwipeController constructor(
  private val context: Context?,
  private val swipeClickActions: SwipeClickActions
) : ItemTouchHelper.Callback() {

  private var swipeBack: Boolean = false
  private var buttonShowedState: ButtonState = GONE
  private var itemViewHolder: ViewHolder? = null
  private var buttonInstance: RectF? = null

  companion object {
    private const val buttonWidth: Float = 200F
  }

  override fun getMovementFlags(
    recyclerView: RecyclerView,
    viewHolder: ViewHolder
  ): Int {
    return makeMovementFlags(0, LEFT or RIGHT)
  }

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: ViewHolder,
    target: ViewHolder
  ): Boolean {
    return false
  }

  override fun onSwiped(
    viewHolder: ViewHolder,
    direction: Int
  ) {
  }

  override fun convertToAbsoluteDirection(
    flags: Int,
    layoutDirection: Int
  ): Int {
    if (swipeBack) {
      swipeBack = buttonShowedState != GONE
      return 0
    }
    return super.convertToAbsoluteDirection(flags, layoutDirection)
  }

  override fun onChildDraw(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    var dx = dX

    if (actionState == ACTION_STATE_SWIPE) {
      if (buttonShowedState != GONE) {
        if (buttonShowedState == LEFT_VISIBLE) {
          dx = dX.coerceAtLeast(
              buttonWidth
          )
        }
        if (buttonShowedState == RIGHT_VISIBLE) {
          dx = dX.coerceAtMost(-buttonWidth)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dx, dY, actionState, isCurrentlyActive)
      } else {
        setTouchListener(c, recyclerView, viewHolder, dx, dY, actionState, isCurrentlyActive)
      }
    }
    if (buttonShowedState == GONE) {
      super.onChildDraw(c, recyclerView, viewHolder, dx, dY, actionState, isCurrentlyActive)
    }
    itemViewHolder = viewHolder
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun setTouchListener(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    recyclerView.setOnTouchListener { _, event ->
      swipeBack = event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP

      if (swipeBack) {
        if (dX < -buttonWidth) buttonShowedState = RIGHT_VISIBLE
        else if (dX > buttonWidth) buttonShowedState = LEFT_VISIBLE
        if (buttonShowedState !== GONE) {
          setTouchDownListener(
              c, recyclerView, viewHolder, dY, actionState, isCurrentlyActive
          )
          setItemsClickable(recyclerView, false)
        }
      }
      false
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun setTouchDownListener(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: ViewHolder,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    recyclerView.setOnTouchListener { _, event ->
      if (event.action == MotionEvent.ACTION_DOWN) {
        setTouchUpListener(c, recyclerView, viewHolder, dY, actionState, isCurrentlyActive)
      }
      false
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun setTouchUpListener(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: ViewHolder,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    recyclerView.setOnTouchListener { _, event ->
      if (event.action == MotionEvent.ACTION_UP) {
        super@SwipeController.onChildDraw(
            c, recyclerView, viewHolder, 0f, dY, actionState, isCurrentlyActive
        )
        recyclerView.setOnTouchListener { _, _ -> false }
        setItemsClickable(recyclerView, true)
        swipeBack = false
        if (buttonInstance != null && buttonInstance!!.contains(
                event.x, event.y
            )
        ) {
          if (buttonShowedState === LEFT_VISIBLE) {
            swipeClickActions.onLeftClicked(viewHolder.adapterPosition)
          } else if (buttonShowedState === RIGHT_VISIBLE) {
            swipeClickActions.onRightClicked(viewHolder.adapterPosition)
          }
        }
        buttonShowedState = GONE
        itemViewHolder = null
      }
      false
    }
  }

  private fun setItemsClickable(
    recyclerView: RecyclerView,
    isClickable: Boolean
  ) {
    for (i in 0 until recyclerView.childCount) {
      recyclerView.getChildAt(i).isClickable = isClickable
    }
  }

  private fun drawButtons(
    c: Canvas,
    viewHolder: ViewHolder
  ) {
    val itemView: View = viewHolder.itemView
    val p = Paint()

    val leftBtnBg = RectF(
        itemView.left.toFloat(),
        itemView.top.toFloat(),
        buttonWidth,
        itemView.bottom.toFloat()
    )

    val rightBtnBg = RectF(
        itemView.right.toFloat() - buttonWidth,
        itemView.top.toFloat(),
        itemView.right.toFloat(),
        itemView.bottom.toFloat()
    )

    context?.let {
      p.color = context.resources!!.getColor(R.color.blueItemBg, null)
      c.drawRect(leftBtnBg, p)
      val leftBitmap = AppCompatResources.getDrawable(context, R.drawable.ic_bookmark)!!
          .toBitmap()
      c.drawBitmap(
          leftBitmap,
          getLeftButtonLeftCoordinate(leftBitmap = leftBitmap),
          getLeftButtonTopCoordinate(itemView = itemView, leftBitmap = leftBitmap),
          p
      )

      p.color = context.resources!!.getColor(R.color.redItemBg, null)
      c.drawRect(rightBtnBg, p)
      val rightBitmap = AppCompatResources.getDrawable(context, R.drawable.ic_star_border)!!
          .toBitmap()
      c.drawBitmap(
          rightBitmap,
          getRightButtonLeftCoordinate(itemView = itemView, rightBitmap = rightBitmap),
          getRightButtonTopCoordinate(itemView = itemView, rightBitmap = rightBitmap),
          p
      )
    }

    if (buttonShowedState === LEFT_VISIBLE) {
      buttonInstance = leftBtnBg
    } else if (buttonShowedState === RIGHT_VISIBLE) {
      buttonInstance = rightBtnBg
    }
  }

  fun onDraw(c: Canvas?) {
    c?.let {
      if (itemViewHolder != null) {
        drawButtons(c, itemViewHolder!!)
      }
    }
  }

  private fun getLeftButtonLeftCoordinate(leftBitmap: Bitmap): Float {
    return buttonWidth / 2 - (leftBitmap.width / 2)
  }

  private fun getLeftButtonTopCoordinate(
    itemView: View,
    leftBitmap: Bitmap
  ): Float {
    return itemView.top.toFloat() + itemView.height / 2 - leftBitmap.height / 2
  }

  private fun getRightButtonLeftCoordinate(
    itemView: View,
    rightBitmap: Bitmap
  ): Float {
    return itemView.right.toFloat() - (rightBitmap.width * 3 / 2)
  }

  private fun getRightButtonTopCoordinate(
    itemView: View,
    rightBitmap: Bitmap
  ): Float {
    return itemView.top.toFloat() + itemView.height / 2 - rightBitmap.height / 2
  }
}