package com.anto.donttouchthespikes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    lateinit var view: DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view = findViewById(R.id.surfaceView)
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