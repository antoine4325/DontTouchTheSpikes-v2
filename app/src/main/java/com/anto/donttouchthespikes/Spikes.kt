package com.anto.donttouchthespikes

import android.graphics.*
import android.graphics.drawable.shapes.RectShape
import androidx.constraintlayout.solver.widgets.Rectangle


class Spikes(val view: DrawingView){
    val paint = Paint()
    var path = Path()
    //val re = RectF(x - halfWidth, y + halfWidth, x + halfWidth, y - halfWidth)
    //var re = RectF(100F, 100F, 400F, 600F)
    //val liste1: MutableList<RectF> = mutableListOf(re, re)
    //val liste2: MutableList<RectF> = mutableListOf(re, re)


     fun drawSpikeParoi() {
         var x = 105F
         var y = 105F
         val width = 125F
         val halfWidth = width / 2
         val nbrSpikes = 8

         for (i in 1..nbrSpikes) {
             path.moveTo(x, y + halfWidth)
             path.lineTo(x - halfWidth, y - halfWidth)
             path.lineTo(x + halfWidth, y - halfWidth)
             path.moveTo(x, y + halfWidth)
             path.close()

             paint.color = Color.DKGRAY
             x += width
         }
         x = 105F
         y = view.screenHeight - 105F

         for (i in 1..nbrSpikes) {
             path.moveTo(x, y - halfWidth)
             path.lineTo(x - halfWidth, y + halfWidth)
             path.lineTo(x + halfWidth, y + halfWidth)
             path.moveTo(x, y - halfWidth)
             path.close()

             paint.color = Color.DKGRAY
             x += width
         }
     }
     fun drawSpikesLeft(nbrSpikes: Int, k: Int) {
         val x = 110F
         var y = 280F
         val width = 125F
         val halfWidth = width / 2
         if (view.oiseau.vx <= 0) {
             //val elem = liste1.size
             //liste1.dropLast(elem-1)
             for (i in 1..nbrSpikes) {
                 path.moveTo(x - halfWidth, y + halfWidth)
                 path.lineTo(x - halfWidth, y - halfWidth)
                 path.lineTo(x , y)
                 path.moveTo(x - halfWidth, y + halfWidth)
                 path.close()
                 //view.canvas.save()
                 //view.canvas.rotate(45F)
                 //re = RectF(x - halfWidth, y + halfWidth, x + halfWidth, y - halfWidth)
                 //view.canvas.restore()
                 //liste1.add(re)
                 paint.color = Color.DKGRAY
                 y = 280F + (k * width)
             }
         }


     }
     fun drawSpikesRight(nbrSpikes: Int, k: Int){
         val x = view.screenWidth - 50F
         var y = 280F
         val width = 125F
         val halfWidth = width / 2
         if (view.oiseau.vx >= 0) {
             //val elem = liste2.size
             //liste2.dropLast(elem-1)
             for (i in 1..nbrSpikes) {
                 path.moveTo(x - halfWidth, y)
                 path.lineTo(x ,y - halfWidth)
                 path.lineTo(x ,y + halfWidth)
                 path.moveTo(x - halfWidth, y)
                 path.close()
                 //view.canvas.save()
                 //view.canvas.rotate(45F)
                 //re = RectF(x - halfWidth, y + halfWidth, x + halfWidth, y - halfWidth)
                 //view.canvas.restore()
                 //liste1.add(re)
                 paint.color = Color.DKGRAY
                 y = 280F + (k * width)
             }
         }

     }


}