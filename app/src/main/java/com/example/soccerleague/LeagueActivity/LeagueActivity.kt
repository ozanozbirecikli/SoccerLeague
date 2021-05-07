package com.example.soccerleague.LeagueActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.soccerleague.R

class LeagueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)
    }
}