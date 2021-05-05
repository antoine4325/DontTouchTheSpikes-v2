package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.content.Context
import android.graphics.*
import android.view.View
import com.google.android.material.shape.TriangleEdgeTreatment
import kotlin.random.Random
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*
import kotlin.coroutines.*

class Oiseau(val echelle : Float, val view: DrawingView, context: Context): View(context) {
    val oiseauPaint = Paint()
    val re0 = RectF(0f, 0f, 0f, 0f)
    var niveau = 1
    var vx=0F
    var vy=0F
    var ay =0F
    var bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.oiseau)
    val r = RectF(0F+300F, 0F+500F, 95F+300F, 63F+500F)
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
        ay= 3000F
        if (flipped) bmp=bmp.flip(-1f, 1f)
    }

    fun firstSet(sW: Float, sH: Float) {
        r.left = sW/2
        r.top = sH/2
        r.right = sW/2 + 95*echelle
        r.bottom = sH/2 + 63*echelle
        vx = 0F
        vy = 0F
        ay = 0F
    }

    fun dessine(canvas: Canvas) {
        canvas.drawBitmap(bmp, null, r, null)
    }

    fun changeDirectionx() {
        bmp = bmp.flip(-1f, 1f)
        vx = -vx
        if (vx > 0) vx += 7
        r.offset(vx*0.01f, 0F)
        view.nbrTouche ++
        view.checkColor()

        if (view.nbrTouche == view.bonbon.nextVisible) {
            if (vx < 0) {
                view.bonbon.carre.offsetTo(150f, random.nextFloat()*view.screenHeight*0.8f+150)
            }
            else {
                view.bonbon.carre.offsetTo(view.screenWidth-270f, random.nextFloat()*view.screenHeight*0.8f+150)
            }
            view.bonbon.visible = true
        }

        for (n in 1.rangeTo(5)) {
            if ( (view.nbrTouche >= (n-1)*10) && (view.nbrTouche <= n*10) ) {
                niveau = n
            }
            else if (view.nbrTouche >= 50) niveau = 5
        }

    }

    fun update(interval: Float) {
        vy+=interval*ay
        r.offset(vx*interval, vy*interval)

        if ( RectF.intersects(r, view.parois[0].paroi)
                    || RectF.intersects(r, view.parois[1].paroi) ) {
                changeDirectionx()
                view.spikes.path.reset()
                view.spikes.drawSpikeParoi()
                view.spikes.drawSpikesLeft()
                view.spikes.drawSpikesRight()
            }


        else if (r.contains(view.spikes.rhaut)
                    || r.contains(view.spikes.rbas)) view.gameOver()

        else if (RectF.intersects(r, view.parois[2].paroi)
                    || RectF.intersects(r, view.parois[3].paroi)) {
            view.nbrVies --
            if (view.nbrVies > 0) {
                r.offsetTo(view.screenWidth/2, view.screenHeight/2)
                touch()
            }
        }
        
        else if (view.bonbon.visible && RectF.intersects(r, view.bonbon.carre)) {
            view.bonbon.touch()
        }

        for (rect in view.spikes.liste1) {
            if (r.contains(rect)) {
                view.nbrVies --
                if (view.nbrVies > 0) {
                    r.offsetTo(view.screenWidth/2, view.screenHeight/2)
                    touch()
                }
                view.spikes.liste1 = mutableListOf(re0)
            }

        }

        for (rect in view.spikes.liste2) {
            if (r.contains(rect)) {
                view.nbrVies --
                if (view.nbrVies > 0) {
                    r.offsetTo(view.screenWidth/2, view.screenHeight/2)
                    touch()
                }
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