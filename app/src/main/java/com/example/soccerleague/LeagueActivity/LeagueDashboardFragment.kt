package com.example.soccerleague.LeagueActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.soccerleague.Database.MatchDatabase
import com.example.soccerleague.R
import com.example.soccerleague.Supporters.Utils

class LeagueDashboardFragment : Fragment() {

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_league_dashboard, container, false)
        mDefineView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        view.findViewById<TextView>(R.id.draw_fixture).setOnClickListener {
            navController!!.navigate(R.id.action_createLeagueFragment_to_fixtureFragment)
        }

        view.findViewById<TextView>(R.id.teams).setOnClickListener {
            navController!!.navigate(R.id.action_createLeagueFragment_to_teamsFragment)
        }

        view.findViewById<TextView>(R.id.delete_league).setOnClickListener {
            var databaseManager = MatchDatabase.getInstance(getContext() as Context)
            databaseManager.matchDao().deleteAllMatches()
            Utils.ExitUser(context as Context)
        }
    }

    fun mDefineView(view: View) {

        val preferences =
            context?.getSharedPreferences("DARKMODEPREFERENCE", Context.MODE_PRIVATE)
        val preferenceEdit = preferences?.edit()
        val isNightModeOn:Boolean = preferences!!.getBoolean("night_mode_on", false)

        val darkModeSwitch = view.findViewById<Switch>(R.id.enable_dark_mode)
        if(isNightModeOn)
            darkModeSwitch.isChecked = true

        darkModeSwitch.setOnClickListener {

            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                preferenceEdit?.putBoolean("night_mode_on", false)
                preferenceEdit?.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                preferenceEdit?.putBoolean("night_mode_on", true)
                preferenceEdit?.apply()
            }


        }
    }

}