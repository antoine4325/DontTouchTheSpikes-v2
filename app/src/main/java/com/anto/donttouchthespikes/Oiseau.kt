package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import java.util.logging.Handler
import kotlin.coroutines.*

class Oiseau(x: Float, y: Float, diametre : Float, val view: DrawingView) {
    val oiseauPaint = Paint()

    val r = RectF(x, y, x+diametre, y + diametre)   //rectangle de l'oiseau
    var vx = 700F
    var vy = -1150F
    val ay = 3000F

    init {
        oiseauPaint.color=Color.RED
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, oiseauPaint)
    }

    fun changeDirectionx() {
        this.vx = -vx
        r.offset(0.01F*vx, 0F)
        view.nbrTouche ++
        view.checkColor()
    }

    fun update(lesParois: Array<Paroi>, interval: Float) {
        vy+=interval*ay
        r.offset(vx*interval, vy*interval)
        if (RectF.intersects(r, lesParois[0].paroi)) changeDirectionx()
        else if (RectF.intersects(r, lesParois[1].paroi)) changeDirectionx()

    }

    fun touch() {
        vy =-1150F
    }



}