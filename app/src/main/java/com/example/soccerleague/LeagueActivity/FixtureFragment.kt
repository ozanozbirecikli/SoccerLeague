package com.example.soccerleague.LeagueActivity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.soccerleague.Database.MatchModel
import com.example.soccerleague.Database.TeamModel
import com.example.soccerleague.LeagueActivity.Weeks.FirstWeekFragment
import com.example.soccerleague.LeagueActivity.Weeks.SecondWeekFragment
import com.example.soccerleague.R
import com.example.soccerleague.Requests.GetTeamNames
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import java.util.Collections.shuffle

class FixtureFragment : Fragment() {

    var teams: List<TeamModel> = ArrayList<TeamModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fixture, container, false)
        mGetTeams(view)

        return view
    }

    fun initFragments(view: View) {

        if (teams.size == 0) {
            return
        }

        val fragmentlist:ArrayList<Fragment> = ArrayList<Fragment>()
//            arrayListOf<Fragment>(FirstWeekFragment(), SecondWeekFragment(), FirstWeekFragment())

        Log.wtf("teams", "teams: " + teams.toString())
        var numberOfTeams = teams.size
        var numberOfWeeks = (numberOfTeams - 1)
        Log.wtf("week", "numOfWeek: " + numberOfWeeks)

        //var fixture = ArrayList<List<MatchModel>>()

        var halfSize = numberOfTeams / 2

        var numberOfMatches = numberOfWeeks * (numberOfTeams / 2)
        Log.wtf("matches", "numOfMatches: " + numberOfMatches)

        shuffle(teams)
        Log.wtf("teams", "teams: " + teams.toString())

        var teamsNew: ArrayList<TeamModel> = ArrayList<TeamModel>(teams)
        //teamsNew.removeAt(0)
        var teamsSize = teamsNew.size

        for (week in 0..(numberOfWeeks)) {

            var currentWeek = week + 1
            Log.wtf("week", "week: " + currentWeek)

            var matchesOfWeek = ArrayList<MatchModel>()
            for (idx in 0..(halfSize - 1)) {

                var firstTeamIndex = (week + idx) % teamsSize
                var secondTeamIndex = (week + teamsSize - idx) % teamsSize

                if (idx == 0) {
                    firstTeamIndex = week
                    secondTeamIndex = (week + 4) % teamsSize
                    Log.wtf("teamsize", "teamsize: " + teamsSize)

                }

                Log.wtf("teams", "firstTeam: " + firstTeamIndex)
                Log.wtf("teams", "secondTeam: " + secondTeamIndex)

                var firstTeam = teamsNew.get(firstTeamIndex)
                var secondTeam = teamsNew.get(secondTeamIndex)
                Log.wtf(
                    "result",
                    "first: " + firstTeam + "Second: " + secondTeam
                )
                var match = MatchModel(firstTeam, secondTeam, currentWeek)
                matchesOfWeek.add(match)
            }

            var fragment = FirstWeekFragment()
            fragment.setList(matchesOfWeek)
            fragmentlist.add(fragment)

        }

        val adapter = ViewPagerAdapter(
            fragmentlist,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.viewPager.adapter = adapter
    }

    fun mGetTeams(view: View) {

        val getTeams = GetTeamNames()
        getTeams.getTeamNames(getActivity() as FragmentActivity)
        getTeams.setListener(object : GetTeamNames.Listener {
            override fun sendResponse(meta: Any?) {
                if (meta != null) {
                    val responseList: List<TeamModel> =
                        meta as List<TeamModel>
                    teams = responseList
                    initFragments(view)

                }
            }
        })
    }


}