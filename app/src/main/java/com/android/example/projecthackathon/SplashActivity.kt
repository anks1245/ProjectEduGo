package com.android.example.projecthackathon

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private val DELAY = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(R.layout.activity_splash_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //setting animation
        val imageView: ImageView = findViewById(R.id.splashImage)
        imageView.animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim)

        Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
        }, DELAY.toLong())

    }
}