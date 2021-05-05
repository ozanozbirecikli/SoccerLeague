package com.example.soccerleague.Supporters

import android.content.Context
import android.content.Intent
import com.example.soccerleague.StartingPage.MainActivity

class Utils {

    companion object {

        fun ExitUser(context: Context) {
            val preference = context.getSharedPreferences("STARTPREFERENCE", Context.MODE_PRIVATE)
            val editor = preference.edit().remove("league_started").commit()

            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

        fun mDelayTime():Long
        {
            return 1200
        }


    }
}