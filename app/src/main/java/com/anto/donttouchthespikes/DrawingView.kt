package com.anto.donttouchthespikes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.logging.Handler
import kotlin.random.Random

class DrawingView @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    val backgroundPaint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f
    var drawing = false
    lateinit var thread: Thread
    var totalElapsedTime: Double = 0.0
    var gameOver = false
    var nbrTouche = 0
    val nbrSlotsPiques = 12
    val random = Random
    val couleurs = arrayOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY,
            Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW)
    val mp = MediaPlayer.create(context, R.raw.le_temps_est_bon)
    var parois: Array<Paroi> = arrayOf(Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f))
    var oiseau = Oiseau(450F,750F,100F, this)

    init {
        backgroundPaint.color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        parois = arrayOf(Paroi(0f, 0f, 50f, screenHeight), //gauche
            Paroi(screenWidth-50f, 0f, screenWidth, screenHeight), //droite
            Paroi(0f,0f, screenWidth, 50f), //haut
            Paroi(0f, screenHeight-50f, screenWidth, screenHeight) //bas
        )
        oiseau = Oiseau(450F,750F,100F, this)

        newGame()
    }
  
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        if (action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE) {
            oiseau.touch()

        }
        return true
    }

    fun updatePositions(elapsedTimeMS: Double) {
        val interval = (elapsedTimeMS / 1000.0).toFloat()
        oiseau.update(parois, interval)
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                canvas.height.toFloat(), backgroundPaint)
            for (i in parois) i.draw(canvas)
            oiseau.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun gameOver() {
        drawing = false

        gameOver = true
    }

    fun newGame() {
        totalElapsedTime = 0.0

        drawing = true
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
    }

    fun checkColor() {
        if (nbrTouche%5 == 0) backgroundPaint.color=couleurs[random.nextInt(0, couleurs.size)]
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS = (currentTime-previousFrameTime).toDouble()
            totalElapsedTime = elapsedTimeMS / 1000.0
            updatePositions(elapsedTimeMS)
            draw()
            previousFrameTime = currentTime
        }
    }

    fun pause() {
        drawing = false
        thread.join()
        mp.pause()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
        mp.start()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

}
