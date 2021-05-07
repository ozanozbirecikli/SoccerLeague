package com.example.soccerleague.LeagueActivity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.soccerleague.Database.MatchModel
import com.example.soccerleague.R
import kotlinx.android.synthetic.main.model_team_item.view.*
import kotlinx.android.synthetic.main.model_week_item.view.*

class WeekFragment() : Fragment() {

    lateinit var mWeekRecyclerView: RecyclerView
    var halfTime: Boolean = false

    var matchesList = ArrayList<MatchModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setList(matchesList: ArrayList<MatchModel>) {
        this.matchesList = matchesList
    }

    fun setHalf() {
        halfTime = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_week, container, false)

        mDefineView(view)
        return view
    }

    fun mDefineView(view: View) {

        mWeekRecyclerView = view.findViewById(R.id.Week_RecyclerView)

        if (halfTime) {
            mWeekRecyclerView.visibility = View.GONE
            val halfText = view.findViewById<TextView>(R.id.half_text)
            halfText.visibility = View.VISIBLE
            view.findViewById<RelativeLayout>(R.id.top_main_layout)
                .setBackgroundColor(ContextCompat.getColor(context as Context, R.color.colorThird))
            view.findViewById<TextView>(R.id.toolbar_title).setText("Half Time")
            view.findViewById<TextView>(R.id.week).visibility = View.GONE

        } else {

            if (matchesList.size != 0)
                view.findViewById<TextView>(R.id.week).text = matchesList.first().week.toString()


            mWeekRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    mWeekRecyclerView.getContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            mWeekRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayout.VERTICAL, false)

            mWeekRecyclerView.adapter = mWeekAdapter(matchesList)
        }
    }

    class mWeekAdapter(val mList: List<MatchModel>) :
        RecyclerView.Adapter<mWeekAdapter.WeeksViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeksViewHolder {

            var adapterView = R.layout.model_week_item

            val v = LayoutInflater.from(parent.context)
                .inflate(adapterView, parent, false)

            return WeeksViewHolder(v)

        }

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun onBindViewHolder(holder: WeeksViewHolder, position: Int) {
            val obj = mList[position]

            var firstTeam = obj.teamFirst
            var secondTeam = obj.teamSecond

            holder.itemView.first_team_name.text = firstTeam
            holder.itemView.second_team_name.text = secondTeam


        }

        class WeeksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    }

}