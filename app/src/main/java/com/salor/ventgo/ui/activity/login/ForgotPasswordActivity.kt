package com.salor.ventgo.ui.activity.login

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.support.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.salor.ventgo.R
import com.salor.ventgo.helper.Cons
import com.salor.ventgo.helper.Loading
import com.salor.ventgo.helper.See
import com.salor.ventgo.service.ApiClient
import com.salor.ventgo.ui.activity.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.item_dialog_forgot_pass_success.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.IOException

class ForgotPasswordActivity : BaseActivity() {

    lateinit var pLoading : Loading
    var str_email : String = ""

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        pLoading = Loading(this)
        setStatusBarGradiantLogin(this)

        setAnimHeader()

        tvBackLogin.setOnClickListener {
            onBackPressed()
        }

        btnSend.setOnClickListener {

            str_email = etEmail.text.toString()

            if (str_email == ""){

                etEmail.requestFocus()
                etEmail.error = resources.getString(R.string.label_form_wajib_diisi)

            }else{

                forgotPassword()

            }

        }

    }

    private fun forgotPassword() {
        pLoading.showLoading(resources.getString(R.string.label_loading_title_dialog), false)
        val service = ApiClient.getClient()
        val gson = Gson()

        val call = service.forgotPassword(str_email)
        Log.e("str_email", "" + str_email)

        See.logE(Cons.CALLRESPONSE,""+call.request())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pLoading.dismissDialog()
                if (response.isSuccessful) {
                    try {
                        val respon = response.body()!!.string()
                        val json = JSONObject(respon)

                        See.logE("respon_forgot", respon)

                        val api_status = json.getInt(Cons.API_STATUS)
                        val api_message = json.getString(Cons.API_MESSAGE)

                        if (api_status == Cons.INT_STATUS) {

                            dialogForgotSuccess()

                        } else {
                            Toast.makeText(this@ForgotPasswordActivity, api_message, Toast.LENGTH_SHORT).show()
                            Log.e("toastlogin", api_message)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    pLoading.dismissDialog()
                    Toast.makeText(this@ForgotPasswordActivity, resources.getString(R.string.label_something_wrong), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pLoading.dismissDialog()
                Toast.makeText(this@ForgotPasswordActivity, resources.getString(R.string.label_koneksi_error), Toast.LENGTH_SHORT).show()
            }
        })

    }




    fun setAnimHeader() {

        try {
            Handler().postDelayed({

                try {
                    TransitionManager.beginDelayedTransition(lParentContent)
                    tvSubHeader2.visibility = View.VISIBLE
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, 650)
        } catch (e: Exception) {
            e.printStackTrace()
            tvSubHeader2.visibility = View.VISIBLE
        }

    }

    fun dialogForgotSuccess() {
        try {
            val dialog = Dialog(this, R.style.DialogLight)
            dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.item_dialog_forgot_pass_success)
            dialog.setCancelable(false)

            var btnOke = dialog.btnOke

            btnOke.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                onBackPressed()

            })

            val size = Point()
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getSize(size)
            val mWidth = size.x

            val window = dialog.window
            val wlp = window!!.attributes as WindowManager.LayoutParams

            wlp.gravity = Gravity.CENTER
            wlp.x = 0
            wlp.y = 0
            wlp.width = mWidth
            window.attributes = wlp
            dialog.show()
        }catch (e : Exception){
            e.printStackTrace()
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setOveridePendingTransisi(this)
    }
}
