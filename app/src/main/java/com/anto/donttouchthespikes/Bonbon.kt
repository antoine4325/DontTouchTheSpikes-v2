package com.anto.donttouchthespikes

import android.content.Context
import android.graphics.*
import android.view.View

class Bonbon(context: Context): View(context) {
    var visible = true
    var bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bonbon)
    val carre = RectF(50f, 100f, 170f, 220f)

    fun dessine(canvas: Canvas) {
        if (visible) canvas.drawBitmap(bmp, null, carre, null)
    }

    fun reset() {
        visible = true
    }
}