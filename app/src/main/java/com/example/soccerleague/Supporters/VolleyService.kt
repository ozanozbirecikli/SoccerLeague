package com.example.soccerleague.Supporters

import android.app.Application
import android.text.TextUtils
import org.json.JSONObject
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray


class VolleyService : ServiceInterface {

    val TAG = VolleyService::class.java.simpleName

    override fun post(
        path: String,
        params: JSONObject,
        completionHandler: (response: JSONObject?) -> Unit
    ) {
        val jsonObjReq = object : JsonObjectRequest(Method.POST, path, params,
            Response.Listener<JSONObject>
            { response ->
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun get(path: String, completionHandler: (response: JSONArray?) -> Unit) {
        val jsonArrReq = object : JsonArrayRequest(Method.GET, path, null,
            Response.Listener<JSONArray>
            { response ->
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/get request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonArrReq, TAG)
    }

    override fun getById(path: String, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonArrReq = object : JsonObjectRequest(Method.GET, path, null,
            Response.Listener<JSONObject>
            { response ->
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/get request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        BackendVolley.instance?.addToRequestQueue(jsonArrReq, TAG)
    }

}

interface ServiceInterface {
    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, completionHandler: (response: JSONArray?) -> Unit)
    fun getById(path: String, completionHandler: (response: JSONObject?) -> Unit)
}

class APIController constructor(serviceInjection: ServiceInterface) :
    ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun post(
        path: String,
        params: JSONObject,
        completionHandler: (response: JSONObject?) -> Unit
    ) {
        service.post(path, params, completionHandler)
    }

    override fun get(path: String, completionHandler: (response: JSONArray?) -> Unit) {
        service.get(path, completionHandler)
    }
    override fun getById(path: String, completionHandler: (response: JSONObject?) -> Unit) {
        service.getById(path, completionHandler)
    }
}

class BackendVolley : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue?.add(request)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = TAG
        requestQueue?.add(request)
    }

    fun cancelPendingRequests(tag: Any) {
        if (requestQueue != null) {
            requestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        private val TAG = BackendVolley::class.java.simpleName

        @get:Synchronized
        var instance: BackendVolley? = null
            private set
    }
}
