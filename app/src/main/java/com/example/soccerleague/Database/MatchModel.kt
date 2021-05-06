package com.example.soccerleague.Database

data class MatchModel(
    var teamFirst: TeamModel,
    var teamSecond: TeamModel,
    var week: Int
)
