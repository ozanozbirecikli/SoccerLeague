package com.example.soccerleague.LeagueActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.soccerleague.R

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

        view.findViewById<TextView>(R.id.score_board).setOnClickListener {
            navController!!.navigate(R.id.action_createLeagueFragment_to_scoreBoardFragment)
        }
    }

    fun mDefineView(view: View) {
    }

}