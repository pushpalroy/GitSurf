package com.gitsurfer.gitsurf.utils.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.State
import com.gitsurfer.gitsurf.R
import kotlin.math.roundToInt

class DividerItemDecorator constructor(
  private val context: Context?,
  private val divider: Drawable
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: State
  ) {
    super.getItemOffsets(outRect, view, parent, state)

    if (parent.getChildAdapterPosition(view) == 0) {
      return
    }

    outRect.top = divider.intrinsicHeight;
  }

  override fun onDraw(
    c: Canvas,
    parent: RecyclerView,
    state: State
  ) {
    super.onDraw(c, parent, state)

    val dividerLeft: Int =
      (parent.paddingStart + context?.resources!!.getDimension(R.dimen.dimen_65dp)
          ).roundToInt()
    val dividerRight: Int =
      (parent.width - parent.paddingEnd - context.resources!!.getDimension(R.dimen.dimen_10dp)
          ).roundToInt()
    val childCount = parent.childCount

    for (i in 0 until childCount - 1) {
      val child = parent.getChildAt(i)
      val params: LayoutParams = child.layoutParams as LayoutParams
      val dividerTop = child.bottom + params.bottomMargin
      val dividerBottom = dividerTop + divider.intrinsicHeight
      divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
      divider.draw(c)
    }
  }
}