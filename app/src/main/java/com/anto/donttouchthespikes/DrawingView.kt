package com.anto.donttouchthespikes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.logging.Handler

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
    var parois: Array<Paroi> = arrayOf(Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f))
    var oiseau = Oiseau(450F, 750F, 100F)

    init {
        backgroundPaint.color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        parois = arrayOf(Paroi(0f, 0f, 50f, screenHeight),
            Paroi(screenWidth, 0f, screenWidth-50f, screenHeight),
            Paroi(0f,0f, screenWidth, 50f),
            Paroi(0f, screenHeight, screenWidth, screenHeight-50f)
        )
        oiseau = Oiseau(screenWidth/2,screenHeight/2,100F)

        newGame()
    }
  
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        if (action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE) {
            OiseauTouch()

        }
        return true
    }

    fun OiseauTouch() {
        oiseau.touch()
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
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

}
