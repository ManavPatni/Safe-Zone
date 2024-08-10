package com.thecodeproject.`in`.safezone

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class TimelineDecoration : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 4f
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft.toFloat() + 20  // Adjust to match your marker's position
        val right = left

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val nextChild = parent.getChildAt(i + 1)
            val top = child.bottom.toFloat()
            val bottom = nextChild.top.toFloat()

            c.drawLine(left, top, right, bottom, paint)
        }
    }
}
