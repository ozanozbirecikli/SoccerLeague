package com.example.soccerleague.StartingPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.soccerleague.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}