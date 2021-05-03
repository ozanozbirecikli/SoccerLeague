package com.example.soccerleague.Supporters

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import com.example.soccerleague.R

class DialogSupport {

    internal var gCustomDialog: Dialog? = null

    fun showLoadingDialog(mActivity: Activity) {

        gCustomDialog = Dialog(mActivity)
        gCustomDialog!!.setCancelable(false)

        gCustomDialog!!.setContentView(R.layout.dialog_view)
        gCustomDialog!!.setTitle("Yükleniyor!")
        gCustomDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        gCustomDialog!!.show()
    }

    fun DismissLoadingDialog() {
        try {
            gCustomDialog!!.dismiss()

        } catch (ex: Exception) {
            Log.e("dismissError", ex.toString())
        }

    }
/*
    fun showPositiveSneakBar(mActivity: FragmentActivity, title: String, message: String) {
        Sneaker.with(mActivity)
            .setTitle(title,R.color.colorWhite)
            .setMessage(message,R.color.colorWhite)
            .setDuration(4000)
            .autoHide(true)
            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            .setCornerRadius(10, 20)
            .sneak(R.color.colorPrimary)


    }

    fun showNegativeSneakBar(mActivity: FragmentActivity, title: String, message: String) {
        Sneaker.with(mActivity)
            .setTitle(title,R.color.colorWhite)
            .setMessage(message,R.color.colorWhite)
            .setDuration(4000)
            .autoHide(true)
            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            .setCornerRadius(10, 20)
            .sneak(R.color.colorRed)

    }

    fun showAcceptResponseDialog(mActivity: FragmentActivity, response_id: Int) {
        val dialog = MaterialDialog(mActivity)
            .title(R.string.acceptResponseTitle)
            .message(R.string.acceptResponseMessage)
            .cornerRadius(10f)

            .negativeButton(text = "Evet") { dialog ->

                var acceptResponse = AcceptResponse()
                acceptResponse.acceptResponse(mActivity as FragmentActivity, response_id)
                acceptResponse.setListener(object : AcceptResponse.Listener {
                    override fun sendResponse(meta: Any?) {
                        if (meta != null) {

//                            Toast.makeText(
//                                mActivity,
//                                "Teklifi Kabul Ettiniz",
//                                Toast.LENGTH_SHORT
//                            )
//                                .show()

                            val dialog = DialogSupport()
                            dialog.showPositiveSneakBar(
                                mActivity,
                                "Fiyat Teklifi",
                                "Tebrikler! Fiyat Teklifini Kabul Ettiniz"
                            )

                            var handler = Handler()
                            handler.postDelayed(
                                object : Runnable {
                                    override fun run() {
                                        mActivity.onBackPressed()
                                        mActivity.onBackPressed()
                                    }
                                },
                                Utils.mDelayTime()
                            )
                        }
                    }
                })
            }

            .positiveButton(text = "Hayır") { dialog ->

            }


        dialog.show()
    }*/
}