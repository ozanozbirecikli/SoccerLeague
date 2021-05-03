package com.example.soccerleague.Requests

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.soccerleague.Supporters.APIController
import com.example.soccerleague.Supporters.DialogSupport
import com.example.soccerleague.Supporters.VolleyService
import com.google.gson.Gson
import org.json.JSONObject

class GetTeamNames {

    lateinit var mActivity: FragmentActivity
    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun sendResponse(meta: Any?)
    }

    fun getTeamNames(activity: FragmentActivity) {

        mActivity = activity

        var dialog = DialogSupport()
        val service = VolleyService()
        val apiController = APIController(service)
        val path = RequestUrls.teamNames
        val params = JSONObject()
        lateinit var responseTeams: List<TeamName>

        dialog.showLoadingDialog(mActivity)

        apiController.get(path) { response ->
            if (response != null) {

                val gson = Gson()
                Log.wtf("OK", "Reponses of Teams Fetched Successfully!")

                responseTeams =
                    gson.fromJson(response.toString(), Array<TeamName>::class.java).toList()

                listener!!.sendResponse(responseTeams)

                dialog.DismissLoadingDialog()

            } else {
                Log.wtf("Error", "Unexpected Error")
                Toast.makeText(activity, "Hata!", Toast.LENGTH_SHORT)
                    .show()
                dialog.DismissLoadingDialog()

            }

        }
    }

    data class TeamName(

        val id: Int = 0,
        val teamName: String = ""
    )

}