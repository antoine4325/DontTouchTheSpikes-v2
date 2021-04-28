package com.anto.donttouchthespikes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Path
import kotlin.random.Random


class Spikes (val view: DrawingView){
    val paint = Paint()
    var path = Path()

     fun drawSpikeParoi(canvas: Canvas) {
         var x = 105F
         var y = 105F
         var width = 125F
         val halfWidth = width / 2
         val nbrSpikes = 8

         for (i in 1..nbrSpikes) {
             path.moveTo(x, y + halfWidth)
             path.lineTo(x - halfWidth, y - halfWidth)
             path.lineTo(x + halfWidth, y - halfWidth)
             path.moveTo(x, y + halfWidth)
             path.close()

             paint.color = Color.DKGRAY
             canvas.drawPath(path, paint)
             x += width
         }
         x = 105F
         y = canvas.height.toFloat() - 105F

         for (i in 1..nbrSpikes) {
             path.moveTo(x, y - halfWidth)
             path.lineTo(x - halfWidth, y + halfWidth)
             path.lineTo(x + halfWidth, y + halfWidth)
             path.moveTo(x, y - halfWidth)
             path.close()

             paint.color = Color.DKGRAY
             canvas.drawPath(path, paint)
             x += width
         }
     }
     fun drawSpikesLeft(canvas: Canvas, nbrSpikes: Int, k: Int) {
         var x = 105F
         var y = 280F
         val width = 125F
         val halfWidth = width / 2
         if (view.oiseau.vx > 0) {
             for (i in 1..nbrSpikes) {
                 path.moveTo(x - halfWidth, y + halfWidth)
                 path.lineTo(x - halfWidth, y - halfWidth)
                 path.lineTo(x + halfWidth, y)
                 path.moveTo(x - halfWidth, y + halfWidth)
                 path.close()

                 paint.color = Color.DKGRAY
                 canvas.drawPath(path, paint)
                 y = 280F + (k * width)
             }
         }

     }
     fun drawSpikesRight(canvas: Canvas, nbrSpikes: Int, k: Int){
         var x = canvas.width.toFloat() - 105F
         var y = 280F
         val width = 125F
         val halfWidth = width / 2
         if (view.oiseau.vx < 0) {
             for (i in 1..nbrSpikes) {
                 path.moveTo(x - halfWidth, y)
                 path.lineTo(x + halfWidth,y - halfWidth)
                 path.lineTo(x + halfWidth,y + halfWidth)
                 path.moveTo(x - halfWidth, y)
                 path.close()

                 paint.color = Color.DKGRAY
                 canvas.drawPath(path, paint)
                 y = 280F + (k * width)
             }
         }

     }


}