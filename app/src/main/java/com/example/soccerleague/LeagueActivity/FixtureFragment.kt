package com.example.soccerleague.LeagueActivity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.soccerleague.Database.MatchDatabase
import com.example.soccerleague.Database.MatchModel
import com.example.soccerleague.Database.TeamModel
import com.example.soccerleague.R
import com.example.soccerleague.Requests.GetTeamNames
import kotlinx.android.synthetic.main.fragment_fixture.view.*
import java.util.Collections.shuffle


class FixtureFragment : Fragment() {

    var teams: List<TeamModel> = ArrayList<TeamModel>()
    var databaseManager: MatchDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // databaseManager = MatchDatabase.getInstance(getContext() as Context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_fixture, container, false)
        mGetTeams(view)

        return view
    }

    fun initFragments(view: View) {

        if (teams.size == 0) {
            Toast.makeText(
                getActivity(),
                "A problem occurred while fetching team list. Please try again",
                Toast.LENGTH_LONG
            ).show()
            getFragmentManager()?.popBackStack()
        }

        shuffle(teams)
        var fixtureList = setFixture()
        val halfTimeFragment = WeekFragment()
        halfTimeFragment.setHalf()

        fixtureList.add(halfTimeFragment)
        teams.reversed()
        fixtureList.addAll(setFixture())

        val adapter = ViewPagerAdapter(
            fixtureList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.viewPager.adapter = adapter
    }

    fun setFixture(): ArrayList<Fragment> {

        val fragmentlist: ArrayList<Fragment> = ArrayList<Fragment>()

        Log.wtf("teams", "teams: " + teams.toString())
        var numberOfTeams = teams.size
        var numberOfWeeks = (numberOfTeams - 1)
        Log.wtf("week", "numOfWeek: " + numberOfWeeks)


        var halfSize = numberOfTeams / 2

        var numberOfMatches = numberOfWeeks * (numberOfTeams / 2)
        Log.wtf("matches", "numOfMatches: " + numberOfMatches)

        Log.wtf("teams", "teams: " + teams.toString())

        var teamsNew: ArrayList<TeamModel> = ArrayList<TeamModel>(teams)
        var teamsSize = teamsNew.size

        for (week in 0..(numberOfWeeks - 1)) {

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
                var match = MatchModel(0, firstTeam, secondTeam, currentWeek)
                insertMatch(match)
                matchesOfWeek.add(match)
            }
            insertWeek(matchesOfWeek)

            var fragment =
                WeekFragment()
            fragment.setList(matchesOfWeek)
            fragmentlist.add(fragment)

        }

        return fragmentlist
    }

    fun insertMatch(match: MatchModel) {
        // databaseManager?.matchDao()?.insert(match)
    }

    fun insertWeek(week: List<MatchModel>) {
        // databaseManager?.matchDao()?.insertWeek(week)
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