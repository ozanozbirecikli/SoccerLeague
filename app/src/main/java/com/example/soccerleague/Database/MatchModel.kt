package com.example.soccerleague.Database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_table")
data class MatchModel(
    @PrimaryKey(autoGenerate = true)
    var matchId: Int = 0,

    @Embedded
    var teamFirst: TeamModel,

    @Embedded
    var teamSecond: TeamModel,

    @ColumnInfo(name = "week")
    var week: Int
) {

}



