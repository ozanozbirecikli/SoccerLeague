package com.example.soccerleague.Database

import androidx.room.*

@Dao
interface MatchDao {

    @Insert
    fun insert(match: MatchModel)

    @Insert
    fun insertWeek(week: List<MatchModel>)

    @Update
    fun update(match: MatchModel)

    @Delete
    fun delete(match: MatchModel)

    @Query("delete from match_table")
    fun deleteAllMatches()

    @Query("select * from match_table")
    fun getAllMatches():List<MatchModel>
}