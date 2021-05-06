package com.example.soccerleague.LeagueActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccerleague.Database.TeamModel
import com.example.soccerleague.R
import com.example.soccerleague.Requests.GetTeamById
import com.example.soccerleague.Requests.GetTeamNames
import kotlinx.android.synthetic.main.model_team_item.view.*

class TeamsFragment : Fragment() {

    lateinit var mTeamsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        mDefineView(view)
        return view
    }

    @SuppressLint("WrongConstant")
    fun mDefineView(view: View) {

        mTeamsRecyclerView = view.findViewById(R.id.Teams_RecyclerView)
        mTeamsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                mTeamsRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        mTeamsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayout.VERTICAL, false)


        var teams = GetTeamNames()
        teams.getTeamNames(getActivity() as FragmentActivity)
        teams.setListener(object : GetTeamNames.Listener {
            override fun sendResponse(meta: Any?) {
                if (meta != null) {
                    val responseList: List<TeamModel> =
                        meta as List<TeamModel>
                    Log.wtf("response:", "Response is: " + meta.toString())
                    mTeamsRecyclerView.adapter = mTeamAdapter(responseList)
                }
            }
        })


        /* var team5 = GetTeamById()
         team5.getTeam(getActivity() as FragmentActivity, 5)
         team5.setListener(object : GetTeamById.Listener {
             override fun sendResponse(meta: Any?) {

                 Log.wtf("response:", "Team 5 : " + meta.toString())
             }
         })*/

    }

    class mTeamAdapter(val mList: List<TeamModel>) :
        RecyclerView.Adapter<mTeamAdapter.TeamsViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {

            var adapterView = R.layout.model_team_item

            val v = LayoutInflater.from(parent.context)
                .inflate(adapterView, parent, false)

            return TeamsViewHolder(v)

        }

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {

            val obj = mList[position]

            holder.itemView.team_name.text = obj.teamName
            holder.itemView.team_id.text = obj.id.toString()

        }

        class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    }
}