package com.example.soccerleague.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TeamModel::class], version = 1)
abstract class TeamDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao

    companion object {

        //Singleton Design Pattern
        private var instance: TeamDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): TeamDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, TeamDatabase::class.java,
                    "team_database"
                )
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build()

            return instance!!
        }
/*
        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: TeamDatabase) {
            val teamDao = db.teamDao()
                teamDao.insert(TeamModel(5,"team 121", 50))
            }
        }*/
    }
}