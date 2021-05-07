package com.example.soccerleague.LeagueActivity

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


class FixtureViewModel : Fragment() {

    var teams: List<TeamModel> = ArrayList<TeamModel>()
    var databaseManager: MatchDatabase? = null
    lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseManager = MatchDatabase.getInstance(getContext() as Context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_fixture, container, false)
        mView = view
        mGetTeams()

        return view
    }

    fun initFragments() {

        if (teams.size == 0) {
            Toast.makeText(
                getActivity(),
                "A problem occurred while fetching team list. Please try again",
                Toast.LENGTH_LONG
            ).show()
            getFragmentManager()?.popBackStack()
        }


        shuffle(teams)
        var fixtureList = setFixture(false)
        val halfTimeFragment = WeekFragment()
        halfTimeFragment.setHalf()

        fixtureList.add(halfTimeFragment)
        teams.reversed()
        fixtureList.addAll(setFixture(true))

        val adapter = ViewPagerAdapter(
            fixtureList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        mView.viewPager.adapter = adapter
    }

    fun mGetNumberOfTeams(): Int {
//        return teams.size
        return 8
    }

    fun mGetNumberOfWeeks(): Int {
        return mGetNumberOfTeams() - 1
    }

    fun mGetHalfTime(): Int {
        return mGetNumberOfTeams() / 2
    }

    fun setFixture(isSecondHalf:Boolean): ArrayList<Fragment> {

        val fragmentlist: ArrayList<Fragment> = ArrayList<Fragment>()

        Log.wtf("teams", "teams: " + teams.toString())
        Log.wtf("week", "numOfWeek: " + mGetNumberOfWeeks())

        var numberOfMatches = mGetNumberOfWeeks() * (mGetNumberOfTeams() / 2)
        Log.wtf("matches", "numOfMatches: " + numberOfMatches)

        Log.wtf("teams", "teams: " + teams.toString())

        var teamsNew: ArrayList<TeamModel> = ArrayList<TeamModel>(teams)
        var teamsSize = teamsNew.size

        for (week in 0..(mGetNumberOfWeeks() - 1)) {

            var currentWeek = week + 1
            if(isSecondHalf)
                currentWeek += mGetNumberOfWeeks()
            Log.wtf("week", "week: " + currentWeek)

            var matchesOfWeek = ArrayList<MatchModel>()
            for (idx in 0..(mGetHalfTime() - 1)) {

                var firstTeamIndex = (week + idx) % teamsSize
                var secondTeamIndex = (week + teamsSize - idx) % teamsSize

                if (idx == 0) {
                    firstTeamIndex = week
                    secondTeamIndex = (week + 4) % teamsSize
                }

                Log.wtf("teams", "firstTeam: " + firstTeamIndex)
                Log.wtf("teams", "secondTeam: " + secondTeamIndex)

                var firstTeam = teamsNew.get(firstTeamIndex)
                var secondTeam = teamsNew.get(secondTeamIndex)
                Log.wtf(
                    "result",
                    "first: " + firstTeam + "Second: " + secondTeam
                )
                var match = MatchModel(0, firstTeam.teamName, secondTeam.teamName, currentWeek)
//                insertMatch(match)
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
        databaseManager?.matchDao()?.insert(match)
    }

    fun getMatches(): List<MatchModel> {
        return databaseManager!!.matchDao().getAllMatches()
    }

    fun insertWeek(week: List<MatchModel>) {
        databaseManager?.matchDao()?.insertWeek(week)
    }

    fun mCheckRoomDB() {

        var teamsFromRoom: ArrayList<MatchModel> = ArrayList(getMatches())
        Log.wtf("matches", "matches: " + teamsFromRoom.toString())

        if (teamsFromRoom != null && teamsFromRoom.size != 0) {
            if (teamsFromRoom.isNotEmpty()) {
                Log.wtf("matchesfromdb", "matches fetched ")

                Log.wtf("matchesfromdb", "matches size: " + teamsFromRoom.size)

                val fragmentlist: ArrayList<Fragment> = ArrayList<Fragment>()

                var matchPerWeek = (teamsFromRoom.size / 2) / mGetNumberOfWeeks()


                for (j in 0..(mGetNumberOfWeeks() * 2) - 1) {
                    Log.wtf("week", "week: " + j)

                    if(j != 6){
                    var matches:ArrayList<MatchModel> = ArrayList<MatchModel>()
                    for(i in 0..(matchPerWeek - 1)){
                        matches.add(teamsFromRoom.get((j*4)+i))
                    }

                    var fragment = WeekFragment()
                    fragment.setList(matches)
                    fragmentlist.add(fragment)
                    }else{
                        val halfTimeFragment = WeekFragment()
                        halfTimeFragment.setHalf()
                        fragmentlist.add(halfTimeFragment)

                    }
                }

                val adapter = ViewPagerAdapter(
                    fragmentlist,
                    requireActivity().supportFragmentManager,
                    lifecycle
                )
                mView.viewPager.adapter = adapter

            }
        } else {

            initFragments()
        }
    }

    fun mGetTeams() {

        val getTeams = GetTeamNames()
        getTeams.getTeamNames(getActivity() as FragmentActivity)
        getTeams.setListener(object : GetTeamNames.Listener {
            override fun sendResponse(meta: Any?) {
                if (meta != null) {
                    val responseList: List<TeamModel> =
                        meta as List<TeamModel>
                    teams = responseList

                    mCheckRoomDB()
                }
            }
        })
    }


}