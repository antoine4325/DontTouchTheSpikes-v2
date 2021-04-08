package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import java.util.logging.Handler
import kotlin.coroutines.*

class Oiseau(var x: Float, var y: Float, val diametre : Float) {
    val OiseauPaint = Paint()

    val r = RectF(x, y, x+diametre, y + diametre)   //rectangle de l'oiseau
    var dx = 0.5F
    var dy = -0.5F

    init {
        OiseauPaint.color=Color.RED
    }



    fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, OiseauPaint)
    }

    fun changeDirectionx() {
        this.dx = -dx
        r.offset(3.0F*dx, 0F)
    }

    fun update(lesParois: Array<Paroi>) {
        r.offset(5.0f*dx, 5.0f*dy)
        for (p in lesParois){
            p.gereOiseau(this)
        }

    }

    fun touch() {}



}