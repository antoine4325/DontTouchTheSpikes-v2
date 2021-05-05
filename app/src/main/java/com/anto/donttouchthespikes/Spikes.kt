package com.anto.donttouchthespikes

import android.graphics.*
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import androidx.constraintlayout.solver.widgets.Rectangle


class Spikes(val view: DrawingView){
    val paint = Paint()
    var path = Path()
    var re = RectF(0f, 0f, 0f, 0f)
    val re0 = RectF(0f, 0f, 0f, 0f)
    var liste1: MutableList<RectF> = mutableListOf(re0)
    var liste2: MutableList<RectF> = mutableListOf(re0)

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

     fun drawSpikesLeft() {
         var nbresplibre = 6 - view.oiseau.niveau
         //var nbrSpikes = 13 - 2*nbresplibre
         var nbrSpikes = 3
         val x = 110F
         var y = 280F
         val width = 125F
         val halfWidth = width / 2
         var k = (1..12).random()

         if (view.oiseau.vx <= 0) {
             view.spikes.liste1 = mutableListOf(re0)
             for (i in 1..nbrSpikes) {

                 var x1 = x - halfWidth
                 var y1 = y + halfWidth
                 var x2 = x - halfWidth
                 var y2 = y - halfWidth
                 path.moveTo(x1, y1)
                 path.lineTo(x2, y2)
                 path.lineTo(x , y)
                 path.moveTo(x1, y1)
                 path.close()

                 re = RectF(x1 + 10, y1, x + 10 + (width/50), y2)
                 liste1.add(re)
                 paint.color = Color.DKGRAY
                 y = 280F + (k * width)
                 k = (1..12).random()
             }

                 //view.canvas.save()
                 //view.canvas.rotate(45F)
                 //re = RectF(120F - (125F/2), 280F + (3 * 125F) + (125F/2), 120F + (125F/50), 280F + (3 * 125F) - (125F/2))
                 //view.canvas.restore()
             /*path.moveTo(120F - (125F/2), 280F + (3 * 125F) + (125F/2))
             path.lineTo(120F + (125F/50), 280F + (3 * 125F) + (125F/2))
             path.lineTo(120F + (125F/50) , 280F + (3 * 125F) - (125F/2))
             path.lineTo(120F - (125F/2), 280F + (3 * 125F) - (125F/2))
             path.moveTo(120F - (125F/2) , 280F + (3 * 125F) + (125F/2))
             path.close()*/
         }
     }
     fun drawSpikesRight(){
         val x = view.screenWidth - 50F
         var y = 280F
         val width = 125F
         val halfWidth = width / 2
         var nbrSpikes = 6
         var k = (1..12).random()
         if (view.oiseau.vx >= 0) {
             val elem = liste2.size
             view.spikes.liste2 = mutableListOf(re0)
             for (i in 1..nbrSpikes) {
                 path.moveTo(x - halfWidth, y)
                 path.lineTo(x ,y - halfWidth)
                 path.lineTo(x ,y + halfWidth)
                 path.moveTo(x - halfWidth, y)
                 path.close()
                 //view.canvas.save()
                 //view.canvas.rotate(45F)
                 re = RectF(x - halfWidth -10, y + halfWidth, x + 10 + (width/50), y - halfWidth)
                 //view.canvas.restore()
                 liste2.add(re)
                 paint.color = Color.DKGRAY
                 y = 280F + (k * width)
                 k = (1..12).random()
             }
         }

     }


}