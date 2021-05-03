package com.anto.donttouchthespikes

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.random.Random

class Bonbon(context: Context, val view: DrawingView): View(context) {
    var visible = true
    var bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bonbon)
    val carre = RectF(50f, 100f, 170f, 220f)
    var nextVisible = 0
    val random = Random

    fun dessine(canvas: Canvas) {
        if (visible) canvas.drawBitmap(bmp, null, carre, null)
    }

    fun reset() {
        visible = false
        nextVisible = random.nextInt(5, 15)
    }

    fun touch() {
        view.nbrVies++
        visible = false
        nextVisible = view.nbrTouche + random.nextInt(5, 15)
    }
}