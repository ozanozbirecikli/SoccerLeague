package com.example.soccerleague.Database

import androidx.room.*

@Dao
interface TeamDao {

    @Insert
    fun insert(team: TeamModel)

    @Update
    fun update(team: TeamModel)

    @Delete
    fun delete(team: TeamModel)

    @Query("drop table team_table")
    fun deleteAllTeams()

    @Query("select * from team_table order by Score desc")
    fun getScoreList()

    @Query("select * from team_table")
    fun getAllTeams()
}