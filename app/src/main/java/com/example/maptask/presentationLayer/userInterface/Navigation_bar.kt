package com.example.maptask.presentationLayer.userInterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.maptask.R
import com.example.maptask.databinding.ActivityMapsBinding

class Navigation_bar : AppCompatActivity() {
    private lateinit var binding:ActivityMapsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btn_back = findViewById<Button>(R.id.btn_back_arr)

        btn_back.setOnClickListener(){
            Toast.makeText(this,"free_rides", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MapsActivity::class.java))
        }

    }
}