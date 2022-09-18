package com.training.farmapp_0706012110026

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.training.farmapp_0706012110026.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.hide()
        val handler = Handler()
        handler.postDelayed(Runnable {
            val i = Intent(this@SplashScreenActivity, AnimalListActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}