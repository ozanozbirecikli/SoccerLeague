package com.example.soccerleague.Database

import androidx.room.*

@Dao
interface MatchDao {

    @Insert
    fun insert(team: MatchModel)

   /* @Insert
    fun insertWeek(week: List<MatchModel>)
*/
    @Update
    fun update(team: MatchModel)

    @Delete
    fun delete(team: MatchModel)

   /* @Query("drop table match_table")
    fun deleteAllTeams()
*/
    /*@Query("select * from match_table order by Score desc")
    fun getScoreList()
*//*
    @Query("select * from match_table")
    fun getAllTeams():List<MatchModel>*/
}