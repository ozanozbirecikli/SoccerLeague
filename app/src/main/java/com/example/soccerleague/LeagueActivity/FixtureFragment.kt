package com.example.soccerleague.LeagueActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.soccerleague.LeagueActivity.Weeks.FirstWeekFragment
import com.example.soccerleague.LeagueActivity.Weeks.SecondWeekFragment
import com.example.soccerleague.R
import kotlinx.android.synthetic.main.fragment_fixture.view.*

class FixtureFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fixture, container, false)

        val fragmentlist = arrayListOf<Fragment>(FirstWeekFragment(), SecondWeekFragment())
        val adapter = ViewPagerAdapter(
            fragmentlist,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.viewPager.adapter = adapter
        return view
    }


}