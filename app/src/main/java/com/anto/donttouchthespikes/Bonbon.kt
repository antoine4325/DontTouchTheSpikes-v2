package com.anto.donttouchthespikes

import android.content.Context
import android.graphics.*
import android.view.View

class Bonbon(context: Context): View(context) {
    var bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bonbon)
    val carre = Rect(0, 0, 120, 120)
    fun dessine(canvas: Canvas) {
        canvas.drawBitmap(bmp, null, carre, null)
    }
}