package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.fragment.app.FragmentActivity
import java.util.*
import java.util.logging.Handler
import kotlin.coroutines.*



class Oiseau(x: Float, y: Float, val diametre : Float) {
    val oiseauPaint = Paint()

    val r = RectF(x, y, x+diametre, y + diametre)   //rectangle de l'oiseau
    var vx = 700F
    var vy = -1150F
    val ay = 3000F


    init {
        oiseauPaint.color=Color.RED
    }

    fun reset(sW: Float, sH: Float) {
        r.left = sW/2
        r.top = sH/2
        r.right = sW/2 + diametre
        r.bottom = sH/2 + diametre
        vx = 700F
        vy = -1150F
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawOval(r, oiseauPaint)
    }

    fun changeDirectionx() {
        this.vx = -vx
        r.offset(0.01F*vx, 0F)
    }

    fun update(lesParois: Array<Paroi>, interval: Float) {
        vy+=interval*ay
        r.offset(vx*interval, vy*interval)
        for (p in lesParois){
            p.gereOiseau(this)
        }

    }

    fun touch() {
        vy =-1150F
    }



}