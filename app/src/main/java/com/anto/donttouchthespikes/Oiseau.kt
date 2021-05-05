package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.content.Context
import android.graphics.*
import android.view.View
import com.google.android.material.shape.TriangleEdgeTreatment

class Oiseau(x: Float, y: Float, val echelle : Float, val view: DrawingView, context: Context): View(context) {
    val oiseauPaint = Paint()
    //val r = RectF(x, y, x+diametre, y + diametre)   //rectangle de l'oiseau
    val re0 = RectF(0f, 0f, 0f, 0f)
    var niveau = 1
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
        //view.spikes.liste1 = mutableListOf(re0)
        //view.spikes.liste2 = mutableListOf(re0)
        for (n in 1.rangeTo(5)) {
            if ( (view.nbrTouche >= (n-1)*10) && (view.nbrTouche <= n*10) ) {
                niveau = n
            }
        }

    }

    fun update(interval: Float, spikes: Spikes) {

        /*for (rect in spikes.liste1){
            if (r.contains(rect)) view.gameOver()
        }*/


        vy+=interval*ay
        r.offset(vx*interval, vy*interval)
        var m = true



        if ( (RectF.intersects(r, view.parois[0].paroi) && (m == true))
                    || RectF.intersects(r, view.parois[1].paroi) ) {
                changeDirectionx()
                view.spikes.path.reset()
                view.spikes.drawSpikeParoi()
                view.spikes.drawSpikesLeft()
                view.spikes.drawSpikesRight()
            }

        else if (RectF.intersects(r, view.parois[2].paroi)
                    || RectF.intersects(r, view.parois[3].paroi)) view.gameOver()


        for (rect in view.spikes.liste1) {
            if (r.contains(rect)) {
                view.gameOver()
                view.spikes.liste1 = mutableListOf(re0)
            }

        }

        for (rect in view.spikes.liste2) {
            if (r.contains(rect)) {
                view.gameOver()
                view.spikes.liste2 = mutableListOf(re0)
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