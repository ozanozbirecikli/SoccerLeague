package com.example.soccerleague.StartingPage

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.soccerleague.R
import com.example.soccerleague.Supporters.Utils
import com.viewpagerindicator.CirclePageIndicator
import java.util.*
import kotlin.concurrent.schedule

class StartingFragment : Fragment() {

    var navController: NavController? = null
    lateinit var mTimer: Timer

    var current_position = 0
    var mPager: ViewPager? = null

    var textList = arrayOf("Create Random Fixture with Your Teams","See the Teams of Your League","Delete Your League and Restructure")


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
        initViewPager(view)
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

    fun initViewPager(view:View){
        mPager = view.findViewById(R.id.pager) as ViewPager
        mPager!!.adapter = CustomPageAdapter(this.requireContext(), textList)
        slideShow()

        val indicator = view.findViewById(R.id.indicator) as CirclePageIndicator

        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density
        indicator.setRadius(4 * density)
    }

    private fun slideShow() {
        val handler = Handler()
        val runnable = Runnable() {
            kotlin.run {
                if (current_position == textList.size)
                    current_position = 0
                mPager!!.setCurrentItem(current_position++, true)
            }
        }

        mTimer = Timer()
        mTimer.schedule(250, 2500) {
            handler.post(runnable)
        }

    }

    class CustomPageAdapter(val ctx: Context, val textList: Array<String>) : PagerAdapter() {

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object` as RelativeLayout
        }

        override fun getCount(): Int {
            return textList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            //val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.model_swipe_item, container, false)

            val text: TextView = view.findViewById(R.id.swipe_text)
            text.setText(textList[position])
            container.addView(view)

            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as RelativeLayout)
        }

    }

}