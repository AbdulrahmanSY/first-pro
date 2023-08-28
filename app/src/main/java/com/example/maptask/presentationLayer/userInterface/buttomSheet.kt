package com.example.maptask.presentationLayer.userInterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.maptask.databinding.ActivityButtomSheetBinding

class buttomSheet : AppCompatActivity() {
    lateinit var binding: ActivityButtomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}