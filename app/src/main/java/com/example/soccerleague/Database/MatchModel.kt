package com.example.soccerleague.Database

import androidx.room.*

@Entity(tableName = "match_table")
data class MatchModel(
    @PrimaryKey(autoGenerate = true)
    var matchId: Int,

    @ColumnInfo(name = "team_first")
    var teamFirst: String,

    @ColumnInfo(name = "team_second")
    var teamSecond: String,

    @ColumnInfo(name = "week")
    var week: Int
)


