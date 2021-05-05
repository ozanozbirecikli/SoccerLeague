package com.example.soccerleague.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class TeamModel(
    @PrimaryKey
    val id: Int,
    var Name: String,
    var Score: Int
)

