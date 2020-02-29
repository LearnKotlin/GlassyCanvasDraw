package com.learnkotlin.glassycanvasdraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learnkotlin.glassycanvasdraw.view.CanvasGlass

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostResume() {
        super.onPostResume()
        findViewById<CanvasGlass>(R.id.imageview).setOverlayResource(R.drawable.pic_color);
    }
}
