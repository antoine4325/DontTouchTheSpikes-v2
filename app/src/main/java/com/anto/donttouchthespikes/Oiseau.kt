package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.content.Context
import android.graphics.*
import android.view.View
import com.google.android.material.shape.TriangleEdgeTreatment

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

    fun update(interval: Float) {
        vy+=interval*ay
        r.offset(vx*interval, vy*interval)
        if (RectF.intersects(r, view.parois[0].paroi)
                || RectF.intersects(r, view.parois[1].paroi)) {
            changeDirectionx()
            view.spikes.path.reset()
            view.spikes.drawSpikeParoi()
            view.spikes.drawSpikesLeft((1..13).random(),(2..7).random())
            view.spikes.drawSpikesRight((1..5).random(), (2..7).random())

        }

        else if (RectF.intersects(r, view.parois[2].paroi)
                || RectF.intersects(r, view.parois[3].paroi)) view.gameOver()

        for (n in (1 .. view.spikes.liste1.count())) {
            val rect = view.spikes.liste1.get(n)
            if (RectF.intersects(r, rect)) {
                view.gameOver()
            }
        }
        /*for (rect in spikes.liste2) {
            if (RectF.intersects(r, rect)) {
                view.gameOver()
            }
        }*/

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