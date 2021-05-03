package com.anto.donttouchthespikes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var view: DrawingView

    override fun onClick(v: View?) {
        button.visibility = View.INVISIBLE
        view.reset()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view = findViewById(R.id.surfaceView)
        button.setOnClickListener(this)

    }

    override fun onPause() {
        super.onPause()
        view.pause()
    }

    override fun onResume() {
        super.onResume()
        view.resume()
    }
}
