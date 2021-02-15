package com.android.example.projecthackathon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private val DELAY = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //setting animation
        val imageView: ImageView = findViewById(R.id.splashImage)
        imageView.animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim)

        Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
        }, DELAY.toLong())

    }
}