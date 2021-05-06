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
    val rhaut = RectF(0f,0f, view.screenWidth, 200f) //Pics haut
    val rbas = RectF(0f, view.screenHeight - 205f, view.screenWidth, view.screenHeight) //Pics bas
    var liste1: MutableList<RectF> = mutableListOf(re0)
    var liste2: MutableList<RectF> = mutableListOf(re0)
    val nbresplibre = 6 - view.oiseau.niveau
    var nbrSpikes = 13 - 2*nbresplibre
    var k = (1..12).random()
    val width = 125F
    val halfWidth = width / 2

     fun drawSpikeParoi() {
         var x = 105F
         var y = 155F

         for (i in 1..8) {
             path.moveTo(x, y + halfWidth)
             path.lineTo(x - halfWidth, y)
             path.lineTo(x + halfWidth, y)
             path.moveTo(x, y + halfWidth)
             path.close()
             paint.color = Color.DKGRAY
             x += width
         }
         x = 105F
         y = view.screenHeight - 155
         for (i in 1..8) {
             path.moveTo(x, y - halfWidth)
             path.lineTo(x - halfWidth, y)
             path.lineTo(x + halfWidth, y)
             path.moveTo(x, y - halfWidth)
             path.close()
             paint.color = Color.DKGRAY
             x += width
         }
     }

     fun drawSpikesLeft() {
         val x = 110F
         var y = 280F

         if (view.oiseau.vx <= 0) {
             view.spikes.liste1 = mutableListOf(re0)
             for (i in 1..nbrSpikes) {
                 path.moveTo(x-halfWidth, y+halfWidth)
                 path.lineTo(x-halfWidth, y-halfWidth)
                 path.lineTo(x , y)
                 path.moveTo(x-halfWidth, y+halfWidth)
                 path.close()
                 re = RectF(x-halfWidth + 40 , y+halfWidth - 40, x-halfWidth + 60, y-halfWidth + 40)
                 liste1.add(re)
                 paint.color = Color.DKGRAY
                 y = 280F + (k * width)
                 k = (1..12).random()
             }
         }
     }
     fun drawSpikesRight(){
         val x = view.screenWidth - 50F
         var y = 280F

         if (view.oiseau.vx >= 0) {
             view.spikes.liste2 = mutableListOf(re0)
             for (i in 1..nbrSpikes) {
                 path.moveTo(x - halfWidth, y)
                 path.lineTo(x ,y - halfWidth)
                 path.lineTo(x ,y + halfWidth)
                 path.moveTo(x - halfWidth, y)
                 path.close()
                 re = RectF(x - halfWidth , y  + halfWidth - 40, x - halfWidth + 20, y - halfWidth + 40)
                 liste2.add(re)
                 paint.color = Color.DKGRAY
                 y = 280F + (k * width)
                 k = (1..12).random()
             }
         }

     }


}