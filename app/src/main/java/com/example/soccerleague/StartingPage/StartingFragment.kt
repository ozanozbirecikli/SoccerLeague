package com.example.soccerleague.StartingPage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.soccerleague.R
import com.example.soccerleague.Supporters.Utils

class StartingFragment : Fragment() {

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_starting, container, false)
        mDefineView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val darkModePreferences =
            context?.getSharedPreferences("DARKMODEPREFERENCE", Context.MODE_PRIVATE)

        val isNightModeOn: Boolean = darkModePreferences!!.getBoolean("night_mode_on", false)

        if (isNightModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val preferences =
            context?.getSharedPreferences("STARTPREFERENCE", Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        if (editor != null && preferences != null) {
            var started = preferences.getBoolean("league_started", false)
            if (started)
                navController!!.navigate(R.id.action_startingFragment_to_nav_graph_league)

        }

        view.findViewById<TextView>(R.id.start_league).setOnClickListener {
            editor?.putBoolean("league_started", true)
            editor?.commit()
            navController!!.navigate(R.id.action_startingFragment_to_nav_graph_league)
        }
    }

    fun mDefineView(view: View) {

        val opening_animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_down)
        opening_animation.setDuration(Utils.mDelayTime())
        val layout: LinearLayout = view.findViewById(R.id.main_layout)
        layout.setAnimation(opening_animation)

    }

}