package com.anto.donttouchthespikes

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.random.Random

class Oiseau(val echelle : Float, val view: DrawingView, context: Context): View(context) {
    val oiseauPaint = Paint()

    var vx = 700F
    var vy = -1150F
    val ay = 3000F
    var bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.oiseau)
    val r = RectF(0F, 0F, 95F, 63F)
    var flipped = false
    val random = Random

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
        r.offset(vx*0.01f, 0F)
        view.nbrTouche ++
        view.checkColor()
        if (view.nbrTouche == view.bonbon.nextVisible) {
            view.bonbon.carre.offsetTo(150f, random.nextFloat()*view.screenHeight*0.9f+50)
            view.bonbon.visible = true
        }
    }

    fun update(interval: Float) {
        vy+=interval*ay
        r.offset(vx*interval, vy*interval)
        if (RectF.intersects(r, view.parois[0].paroi)
                || RectF.intersects(r, view.parois[1].paroi)) changeDirectionx()
        else if (view.bonbon.visible && RectF.intersects(r, view.bonbon.carre)) {
            view.bonbon.touch()
        }
        else if (RectF.intersects(r, view.parois[2].paroi)
                || RectF.intersects(r, view.parois[3].paroi)) {
            view.nbrVies --
            if (view.nbrVies > 0) {
                r.offsetTo(view.screenWidth/2, view.screenHeight/2)
                touch()
            }
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