package com.anto.donttouchthespikes

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.fragment.app.FragmentActivity
import java.util.*
import java.util.logging.Handler
import kotlin.coroutines.*

class Oiseau(x: Float, y: Float, val echelle : Float, val view: DrawingView, context: Context): View(context) {
    val oiseauPaint = Paint()
    //val r = RectF(x, y, x+diametre, y + diametre)   //rectangle de l'oiseau
    var vx = 700F
    var vy = -1150F
    val ay = 3000F
    var bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.oiseau)
    val r = RectF(0F, 0F, 95F, 63F)
    var flipped = false

    init {
        oiseauPaint.color=Color.RED
    }

    fun reset(sW: Float, sH: Float) {
        r.left = sW/2
        r.top = sH/2
        r.right = sW/2 + 95*echelle
        r.bottom = sH/2 + 63*echelle
        vx = 700F
        vy = -1150F
        if (flipped) bmp=bmp.flip(-1f, 1f)
    }

    fun dessine(canvas: Canvas) {
        canvas.drawBitmap(bmp, null, r, null)
    }

    fun changeDirectionx() {
        bmp = bmp.flip(-1f, 1f)
        vx = -vx
        r.offset(0.01F*vx, 0F)
        view.nbrTouche ++
        view.checkColor()
    }

    fun update(lesParois: Array<Paroi>, interval: Float) {
        vy+=interval*ay
        r.offset(vx*interval, vy*interval)
        if (RectF.intersects(r, lesParois[0].paroi)) {
            changeDirectionx()
        }
        else if (RectF.intersects(r, lesParois[1].paroi)) {
            changeDirectionx()
        }


    }

    fun touch() {
        vy =-1150F
    }

    private fun Bitmap.flip(x: Float, y: Float): Bitmap {
        flipped = when (flipped) {
            true -> false
            false -> true
        }
        val matrix = Matrix().apply { postScale(x, y, width/2f, height/2f) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

}