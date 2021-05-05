package com.example.soccerleague.Database

class TeamRepository(
    private val teamDao: TeamDao
) {

    suspend fun insert(teamModel:TeamModel){
        teamDao.insert(teamModel)
    }

    suspend fun update(teamModel: TeamModel){
        teamDao.update(teamModel)
    }

    suspend fun delete(teamModel: TeamModel){
        teamDao.delete(teamModel)
    }

    suspend fun deleteAll(){
        teamDao.deleteAllTeams()
    }




}