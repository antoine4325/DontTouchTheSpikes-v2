package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Paroi (x1: Float, y1: Float, x2: Float, y2: Float) {
    val paroi = RectF(x1, y1, x2, y2)
    val paroiPaint = Paint()

    fun draw(canvas: Canvas) {
        paroiPaint.color = Color.GRAY
        canvas.drawRect(paroi, paroiPaint)
    }
}
