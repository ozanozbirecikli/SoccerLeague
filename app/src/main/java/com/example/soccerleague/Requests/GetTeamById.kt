package com.example.soccerleague.Requests

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.soccerleague.Database.TeamModel
import com.example.soccerleague.Supporters.APIController
import com.example.soccerleague.Supporters.DialogSupport
import com.example.soccerleague.Supporters.VolleyService
import com.google.gson.Gson
import org.json.JSONObject

class GetTeamById {

    lateinit var mActivity: FragmentActivity
    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun sendResponse(meta: Any?)
    }

    fun getTeam(activity: FragmentActivity, id:Int) {

        mActivity = activity

        var dialog = DialogSupport()
        val service = VolleyService()
        val apiController = APIController(service)
        val path = RequestUrls.teamNames
        val params = JSONObject()
        lateinit var responseTeam: TeamModel

        dialog.showLoadingDialog(mActivity)

        apiController.getById(path + "/" + id) { response ->
            if (response != null) {

                val gson = Gson()
                Log.wtf("OK", "Reponses of Team name Fetched Successfully!")

                responseTeam =
                    gson.fromJson<TeamModel>(response.toString(), TeamModel::class.java)

                listener!!.sendResponse(responseTeam)

                dialog.DismissLoadingDialog()

            } else {
                Log.wtf("Error", "Unexpected Error")
                Toast.makeText(activity, "Hata!", Toast.LENGTH_SHORT)
                    .show()
                dialog.DismissLoadingDialog()

            }

        }
    }

}