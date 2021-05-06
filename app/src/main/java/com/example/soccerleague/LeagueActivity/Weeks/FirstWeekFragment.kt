package com.example.soccerleague.LeagueActivity.Weeks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.soccerleague.Database.MatchModel
import com.example.soccerleague.R

class FirstWeekFragment() : Fragment() {

    var matchesList = ArrayList<MatchModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setList(matchesList: ArrayList<MatchModel>){
        this.matchesList = matchesList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_week, container, false)
        var text = view.findViewById<TextView>(R.id.text)
        text.setText(matchesList.get(0).teamFirst.teamName + " - " + matchesList.get(0).teamSecond.teamName)
        return view
    }


}