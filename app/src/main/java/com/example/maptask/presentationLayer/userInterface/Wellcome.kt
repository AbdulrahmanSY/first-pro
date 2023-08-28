package com.example.maptask.presentationLayer.userInterface

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.maptask.R
import com.example.maptask.databinding.ActivityWellcomeBinding


class Welcome : AppCompatActivity() {
    lateinit var binding: ActivityWellcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWellcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginAccount.setOnClickListener() {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.btnCreateAccount.setOnClickListener() {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.darkTheme) //when dark mode is enabled, we use the dark theme
//        } else {
//            setTheme(R.style.AppTheme)  //default app theme
//        }
//        binding.switchtheme.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }

//        }

        binding.btnCreateAccount.scaleX = 0F
        binding.btnCreateAccount.scaleY = 0F
        binding.btnCreateAccount.animate().scaleYBy(1F).duration = 1500
        binding.btnCreateAccount.animate().scaleXBy(1F).duration = 1500
        binding.btnCreateAccount.alpha = 0f

        //Animate the alpha value to 1f and set duration as 1.5 secs.
        binding.btnCreateAccount.animate().alpha(1f).duration = 3000

        binding.btnCreateAccount.animate().rotation(360F).duration = 100

        binding.btnCreateAccount.alpha = 0f
        binding.btnCreateAccount.translationY = 100F

        binding.btnCreateAccount.animate().alpha(1f).translationYBy(-100F).duration = 1000


        binding.btnLoginAccount.animate().rotation(360F).duration = 100

        binding.btnLoginAccount.scaleX = 0F
        binding.btnLoginAccount.scaleY = 0F
        binding.btnLoginAccount.animate().scaleYBy(1F).duration = 1500
        binding.btnLoginAccount.animate().scaleXBy(1F).duration = 1500

        binding.btnLoginAccount.alpha = 0f
        //Animate the alpha value to 1f and set duration as 1.5 secs.
        binding.btnLoginAccount.animate().alpha(1f).duration = 3000
        binding.btnLoginAccount.alpha = 0f
        binding.btnLoginAccount.translationY = 100F
        binding.btnLoginAccount.animate().alpha(1f).translationYBy(-100F).duration = 1000

        binding.imageView.animate().rotation(720F).duration = 100
        binding.imageView.alpha = 0f
        binding.imageView.translationY = 1500F
//        binding.imageView.translationX = 1500F
        binding.imageView.animate().alpha(1f).translationYBy(-1500F).duration = 1000
//        binding.imageView.animate().alpha(1f).translationXBy(-1500F).duration = 1000
        binding.imageView.scaleX = 0F
        binding.imageView.scaleY = 0F
        binding.imageView.animate().scaleYBy(1F).duration = 1500
        binding.imageView.animate().scaleXBy(1F).duration = 1500

    }
}