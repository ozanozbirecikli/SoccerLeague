package com.example.soccerleague.Database

import android.app.Activity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MatchModel::class], version = 1, exportSchema = false)
abstract class MatchDatabase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    companion object {

        //Singleton Design Pattern
        private var instance: MatchDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MatchDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, MatchDatabase::class.java,
                    "team_database"
                ).allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }

    }
}