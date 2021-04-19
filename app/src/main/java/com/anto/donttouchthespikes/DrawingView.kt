package com.anto.donttouchthespikes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
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
    var parois: Array<Paroi> = arrayOf(Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f))
    var oiseau = Oiseau(450F, 750F, 100F)

    val activity = context as FragmentActivity



    init {
        backgroundPaint.color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        newGame()
    }


    fun reset() {
        parois = arrayOf(Paroi(0f, 0f, 50f, screenHeight), //gauche
                Paroi(screenWidth-50f, 0f, screenWidth, screenHeight), //droite
                Paroi(0f,0f, screenWidth, 50f), //haut
                Paroi(0f, screenHeight-50f, screenWidth, screenHeight) //bas
        )
        oiseau.reset(screenWidth, screenHeight)
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

        for (p in parois){
            if ((p== parois[3]||p==parois[2]) && RectF.intersects(p.paroi,oiseau.r)){
                gameOver()
            }
        }
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
        showGameOverDialog("Vous avez perdu!")
        gameOver = true
    }

    fun newGame() {
        reset()
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





    fun showGameOverDialog(messageId: String) { //changement: Int-> string
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle(messageId)
                builder.setPositiveButton("Redemarrer une partie",
                        DialogInterface.OnClickListener { _, _->newGame()}
                )
                return builder.create()
            }
        }

        activity.runOnUiThread(
                Runnable {
                    val ft = activity.supportFragmentManager.beginTransaction()
                    val prev =
                            activity.supportFragmentManager.findFragmentByTag("dialog")
                    if (prev != null) {
                        ft.remove(prev)
                    }
                    ft.addToBackStack(null)
                    val gameResult = GameResult()
                    gameResult.setCancelable(false)
                    gameResult.show(ft,"dialog")
                }
        )
    }

}
