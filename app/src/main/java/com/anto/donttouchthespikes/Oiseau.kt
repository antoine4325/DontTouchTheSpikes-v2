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
    var dx = 1F
    var dy = 1F

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

    fun update(lesParois: Array<Paroi>, interval: Double) {
        r.offset((500*interval*dx).toFloat(), (500*interval*dy).toFloat())
        for (p in lesParois){
            p.gereOiseau(this)
        }

    }

    fun touch() {
        dy *=-4F
        var ok = true
        val InitialTime = System.currentTimeMillis()
        while (ok){
            val SecondTime = System.currentTimeMillis()
            val Delta = SecondTime - InitialTime
            if (Delta >= 100){
                ok = false
            }
        }
        dy*=-0.25F

    }



}