package com.anto.donttouchthespikes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.logging.Handler
import kotlin.random.Random

class DrawingView @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    val backgroundPaint = Paint()
    val textPaint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f
    var drawing = false
    lateinit var thread: Thread
    var totalElapsedTime: Double = 0.0
    var gameOver = false
    val textPaint = Paint()
    var nbrTouche = 0
    var record = 0
    var firstsetting = false
    val nbrSlotsPiques = 12
    val random = Random
    val couleurs = arrayOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY,
            Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW)
    val mp = MediaPlayer.create(context, R.raw.le_temps_est_bon)
    var parois: Array<Paroi> = arrayOf(Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f),
            Paroi(0f, 0f, 0f, 0f))
    var oiseau = Oiseau(2F, this, context)
    val activity = context as FragmentActivity
    var nbrVies = 1
    val bonbon = Bonbon(context, this)

    init {
        backgroundPaint.color = Color.WHITE
        textPaint.color = Color.BLACK
        textPaint.textSize = 50f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        firstGame()
    }


    fun reset() {
        nbrTouche = 0
        nbrVies = 1
        parois = arrayOf(Paroi(0f, 0f, 50f, screenHeight), //gauche
                Paroi(screenWidth-50f, 0f, screenWidth, screenHeight), //droite
                Paroi(0f,0f, screenWidth, 50f), //haut
                Paroi(0f, screenHeight-50f, screenWidth, screenHeight) //bas
        )
        oiseau.reset(screenWidth, screenHeight)
        bonbon.reset()
        if (firstsetting== false) firstsetting = true
        backgroundPaint.color = Color.WHITE
    }

    fun firstSet() {
        parois = arrayOf(Paroi(0f, 0f, 50f, screenHeight), //gauche
            Paroi(screenWidth-50f, 0f, screenWidth, screenHeight), //droite
            Paroi(0f,0f, screenWidth, 50f), //haut
            Paroi(0f, screenHeight-50f, screenWidth, screenHeight) //bas
        )
        bonbon.reset()
        oiseau.firstSet(screenWidth, screenHeight)
        backgroundPaint.color = Color.WHITE
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        if (action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE) {

            if (firstsetting == true ) oiseau.touch()

        }
        return true
    }

    fun updatePositions(elapsedTimeMS: Double) {
        val interval = (elapsedTimeMS / 1000.0).toFloat()
        oiseau.update(interval)
    }



    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                    canvas.height.toFloat(), backgroundPaint)
            for (i in parois) i.draw(canvas)
            oiseau.dessine(canvas)
            bonbon.dessine(canvas)
            canvas.drawText("Votre score est:   $nbrTouche ",
                    30f, 50f, textPaint)
            canvas.drawText("Vies restantes : $nbrVies", screenWidth/3, screenHeight/3, textPaint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun gameOver() {
        mp.stop()
        mp.prepare()
        drawing = false
        if (nbrTouche>record) {
            record= nbrTouche
            showGameOverDialog("Vous avez battu votre record!!")
        }
        else {
            showGameOverDialog("Vous avez perdu!")
        }
        gameOver = true
    }

    fun newGame() {
        reset()
        drawing = true
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
            mp.start()
        }
    }

    fun firstGame() {
        firstSet()
        drawing = true
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
            mp.start()
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
            if (nbrVies <= 0) gameOver()
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





    fun showGameOverDialog(messageId: String) { //changement: Int-> string
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle(messageId)
                builder.setMessage("Votre score est:   "+ nbrTouche.toString()+ "\n"
                        + "Votre record est:    $record")
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
